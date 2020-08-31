1. By default, system create  wallet with id = 1,2,3 and amount 0

2. API:
a. add money to the wallet:

    
    POST localhost:8080/wallet
        {
             "walletId":1,
            "amount": 200
        }
        
b. subtract money from a wallet:
    
    PUT localhost:8080/wallet
        {
             "walletId":1,
            "amount": 200
        }
        
c. get amount of wallet (change to required walletId)

    GET localhost:8080/wallet/{walletId}

3.for start application run 'gradlew bootRun' task






"Задача:

Реализовать приложение для работы со счетом клиента. Приложение должно поддерживать следующие ф-ии:
- Зачисление денежных средств на счет
- Списание денежных средств со счета
- Получение актуального остатка на счете

Функциональные ограничения:
Значение на счете клиента не может принимать отрицательные значения.


Требования к реализации:
- не использовать constraint базы данных при реализации функциональных ограничений
- покрытие основной функциональности unit-тестами
- фреймворк spring boot
- сборка gradle
- запуск приложения командной строкой (gradle, docker-compose)
- результат должен быть оформлен в виде git-репозитория с кодом приложения с инструкцией по развертыванию приложения и предоставлен в виде ссылки на этот репозиторий."

