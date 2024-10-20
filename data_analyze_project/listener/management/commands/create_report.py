import os
from dotenv import load_dotenv
import pandas as pd
import statsmodels.api as sm
import matplotlib.pyplot as plt
import requests
from reportlab.lib.pagesizes import letter
from reportlab.pdfgen import canvas
from io import BytesIO
from PIL import Image

from .report_sending_producer import ReportSendingProducer

load_dotenv()
service_file_url = os.getenv('SERVICE_FILE_URL', 'http://localhost:8082')


def create_report(json):
    df = pd.read_csv(f'{service_file_url}/files/{json["sourceFileId"]}/download')

    df['open_time'] = pd.to_datetime(df['open_time'], unit='ms')
    df.set_index('open_time', inplace=True)

    prices = df['close']

    model = sm.tsa.AutoReg(prices, lags=7)
    results = model.fit()

    plt.figure(figsize=(10, 5))
    plt.plot(prices, label='Исходные данные')
    plt.plot(results.predict(start=len(prices), end=len(prices) + 7), label='Прогноз', color='red')
    plt.title('AR(7) модель для цен биткоина')
    plt.xlabel('Время')
    plt.ylabel('Цена')
    plt.legend()

    # Сохранение графика в памяти
    img_buf = BytesIO()
    plt.savefig(img_buf, format='png')
    plt.close()  # Закрываем график, чтобы избежать отображения

    # Перемещение указателя в начало буфера
    img_buf.seek(0)

    # Открытие изображения с помощью Pillow
    image = Image.open(img_buf)

    # Генерация PDF-документа
    pdf_path = 'report.pdf'
    c = canvas.Canvas(pdf_path, pagesize=letter)
    width, height = letter

    # Добавление графика в PDF
    image.save("temp_image.png")  # Сохраняем временное изображение
    c.drawImage("temp_image.png", 50, 400, width=500, height=250)  # Позиция и размер изображения

    # Завершение PDF
    c.save()

    # Удаляем временный файл
    os.remove("temp_image.png")

    # Отправка PDF-документа на HTTP-эндпоинт
    with open(pdf_path, 'rb') as pdf_file:
        upload_url = f'{service_file_url}/files'
        files = {'file': ('report.pdf', pdf_file, 'multipart/form-data')}
        response = requests.post(upload_url, files=files)

    json['resultFileId'] = response.json().get('id')
    producer = ReportSendingProducer()
    producer.publish(json)
