import os
from dotenv import load_dotenv
import pandas as pd
import statsmodels.api as sm
import matplotlib.pyplot as plt
import requests
from io import BytesIO

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
    img_buf.seek(0)  # Сброс указателя в начало буфера

    # Отправка графика на HTTP-эндпоинт
    upload_url = f'{service_file_url}/files'
    files = {'file': ('plot.png', img_buf, 'multipart/form-data')}
    response = requests.post(upload_url, files=files)
