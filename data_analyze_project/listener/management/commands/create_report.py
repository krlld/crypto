import os
from dotenv import load_dotenv
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
import requests
from reportlab.lib.pagesizes import letter
from reportlab.pdfgen import canvas
from io import BytesIO
from PIL import Image
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error, r2_score

from .report_sending_producer import ReportSendingProducer

load_dotenv()
service_file_url = os.getenv('SERVICE_FILE_URL', 'http://localhost:8082')

def create_report(json):
    df = pd.read_csv(f'{service_file_url}/files/{json["sourceFileId"]}/download')

    # Преобразование временной метки в datetime
    df['open_time'] = pd.to_datetime(df['open_time'], unit='ms')
    df['close_time'] = pd.to_datetime(df['close_time'], unit='ms')

    # Рассчёт процентного роста
    df['percent_change'] = df['close'].pct_change() * 100  # в процентах
    df.dropna(subset=['percent_change'], inplace=True)

    # Определение независимых и зависимых переменных
    X = df[['open', 'high', 'low', 'volume']]
    y = df['percent_change']

    # Разделение данных
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

    # Обучение модели
    model = LinearRegression()
    model.fit(X_train, y_train)

    # Прогнозирование
    y_pred = model.predict(X_test)

    # Оценка
    rmse = mean_squared_error(y_test, y_pred, squared=False)
    r2 = r2_score(y_test, y_pred)

    # Создание DataFrame для фактических и предсказанных значений
    results = pd.DataFrame({'Actual': y_test, 'Predicted': y_pred})

    # График: Сравнение фактических и предсказанных значений
    plt.figure(figsize=(14, 7))
    plt.plot(results['Actual'].values, label='Фактические значения', color='blue', alpha=0.6)
    plt.plot(results['Predicted'].values, label='Прогнозируемые значения', color='orange', alpha=0.6)
    plt.title('Сравнение фактических и прогнозируемых процентных изменений')
    plt.xlabel('Индекс')
    plt.ylabel('Процентное изменение')
    plt.legend()

    # Сохранение графика в памяти
    img_buf1 = BytesIO()
    plt.savefig(img_buf1, format='png')
    plt.close()

    # График: Распределение ошибок
    errors = results['Actual'] - results['Predicted']
    plt.figure(figsize=(14, 7))
    sns.histplot(errors, bins=30, kde=True, color='red')
    plt.title('Распределение ошибок прогнозирования')
    plt.xlabel('Ошибка')
    plt.ylabel('Частота')
    plt.axvline(0, color='black', linestyle='--')

    # Сохранение графика в памяти
    img_buf2 = BytesIO()
    plt.savefig(img_buf2, format='png')
    plt.close()

    # График: Ошибка предсказания в зависимости от предсказанных значений
    plt.figure(figsize=(14, 7))
    plt.scatter(results['Predicted'], errors, color='purple', alpha=0.5)
    plt.title('Ошибка прогнозирования в зависимости от предсказанных значений')
    plt.xlabel('Прогнозируемые значения')
    plt.ylabel('Ошибка')
    plt.axhline(0, color='black', linestyle='--')

    # Сохранение графика в памяти
    img_buf3 = BytesIO()
    plt.savefig(img_buf3, format='png')
    plt.close()

    # Генерация PDF-документа
    pdf_path = 'report.pdf'
    c = canvas.Canvas(pdf_path, pagesize=letter)

    # Добавление графиков в PDF
    img_buf1.seek(0)
    image1 = Image.open(img_buf1)
    image1.save("temp_image1.png")
    c.drawImage("temp_image1.png", 50, 500, width=500, height=250)

    img_buf2.seek(0)
    image2 = Image.open(img_buf2)
    image2.save("temp_image2.png")
    c.drawImage("temp_image2.png", 50, 250, width=500, height=250)

    img_buf3.seek(0)
    image3 = Image.open(img_buf3)
    image3.save("temp_image3.png")
    c.drawImage("temp_image3.png", 50, 0, width=500, height=250)

    # Добавление новой страницы для метрик
    c.showPage()  # Переход на новую страницу
    c.setFont("Helvetica", 12)
    c.drawString(50, 750, f'RMSE: {rmse:.2f}')
    c.drawString(50, 730, f'R²: {r2:.2f}')

    # Завершение PDF
    c.save()

    # Удаляем временные файлы
    os.remove("temp_image1.png")
    os.remove("temp_image2.png")
    os.remove("temp_image3.png")

    # Отправка PDF-документа на HTTP-эндпоинт
    with open(pdf_path, 'rb') as pdf_file:
        upload_url = f'{service_file_url}/files'
        files = {'file': ('report.pdf', pdf_file, 'multipart/form-data')}
        response = requests.post(upload_url, files=files)

    json['resultFileId'] = response.json().get('id')
    producer = ReportSendingProducer()
    producer.publish(json)