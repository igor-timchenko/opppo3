# Используем официальный образ Python
FROM python:3.9-slim

# Устанавливаем зависимости
RUN pip install psycopg2-binary

# Копируем код приложения в контейнер
COPY main.py /app/main.py

# Устанавливаем рабочую директорию
WORKDIR /app

# Запускаем приложение
CMD ["python", "main.py"]
