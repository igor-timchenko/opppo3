import psycopg2
import os
def main():
    # Получаем параметры подключения из переменных окружения
    db_host = os.getenv('DB_HOST', 'db')
    db_name = os.getenv('DB_NAME', 'mydatabase')
    db_user = os.getenv('DB_USER', 'postgres')
    db_password = os.getenv('DB_PASSWORD', 'password')
    # Подключаемся к базе данных
    connection = psycopg2.connect(
        host=db_host,
        database=db_name,
        user=db_user,
        password=db_password
    )  
    cursor = connection.cursor()  
    # Выполняем запрос к таблице
    cursor.execute("SELECT message FROM test_table;")    
    # Получаем результат
    row = cursor.fetchone()    
    if row:
        print(row[0])
    else:
        print("No data found.")    
    # Закрываем соединение
    cursor.close()
    connection.close()
if __name__ == "__main__":
    main()
