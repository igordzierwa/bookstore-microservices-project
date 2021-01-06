# Book Store
Authors: Adrian Nędza, Igor Dzierwa, Konrad Makuch\

mvn clean package -DskipTests

#ZIPKIN: 
jeśli chcemy uruchomić bez docker compose:
robimy docker run -d -p 9411:9411 openzipkin/zipkin
zipkin odpala się na localhost:9411
odpalamy wszystkie serwisy i w zipkinie widzimy logi związane z odpytywaniem poszczególnych serwerów, 
trzeba wywołać jakaś akcję, np rejestrację, potem w zipkin kliknąć "run query" i powinno się coś pojawić
dodane też do docker-compose - jak odpalimy całość to zipkin też się odpali w dockerze.


#maingateway: localhost:9091
nowy gateway któy działa tak jak poprzedni z tym że w miarę dobrze

#SWAGGER
http://localhost:9091/swagger-ui.html - jeden endpoint dla wszystkich serwisów

#najważniejsze endpointy

**POST /accounts-service/registration**

Rejestracja użytkownika, FormData:
 - username
 - password
 - email
 - name
 - surname

**POST /accounts-service/auth**

Autoryzacja, FormData
- username
- password

w odpowiedzi dostajemy token Bearer
który nalezy wykorzystywać do reqiestów
(postman authorization bearer token) 

są zdefiniowane 4 role - admin, employee, user, notRegisteredUser - przyda się do składania zamówień jako "niezalogowany"

w bearerze jest zaszyta rola i na jej podstawie się regquest autoryzują, analogicznie jak w librario, dużo kodu i konfiguracji przydało się stamtąd też.

w maingateway jest konfiguracja autoryzacji (które role mają do czego dostęp SecutiryTokenConfig.java)

przykładowe:
 - "/accounts-service/logged/test_tylko_admin - musi byc admin
 - "/accounts-service/logged/**" - musi być user
 - "/accounts-service/**"- niezalogowani
   

pozostałe serwisy:
aby przetestować wszystko co jest zrobione trzeba:  
1. dodać autora   
  
**POST /products-service/admin/author FormData**
- name
- file
- description
- year
- 
*2. dodać product (książkę)*
  
**POST /products-service/admin/book FormData**
- name
- author.id
- file
- length
- description
- stock (ilosc w magazynie)
- price
- 
*3. stworzyć koszyk (hipotetycznie jeśli system by działał to przy pierwszym otwarciu strony wykonywalibysmy takie zapytanie o koszyk) - tworzy się w tym momenecie koszyk i dopisuje się Cookie z Id koszyka, które automatycznie jest dodawane do kolejnych requestów. No i jeśli mamy coś w koszyku to ta metoda zwraca aktualny stan koszyka - produkty + sumę.*
  
**GET /baskets-service/cart**

*4. dodać do koszyka (książkę)*
  
**POST /baskets-service/cart/{ID PRODUKTU} **

*5. odczytać koszyk albo usunąć produkt - Delete*
**GET /baskets-service/cart/{id produktu do usunięcia}**
tutaj ważne - nie podajemy ID produktu z products-service tylko z baskets-service - Itemy w koszyku mają swoje ID i niekonieczni ejest takie samo jak id produktu

*6. Złożyć zamówienie*
**POST /orders-service/order/**
i po wykonaniu tej metody z zapisanym cookie 
- sprawdzają sie stany magazynowe - czy można złożyć zamówienie
- zapisuje się zamówienie
- jeśli wykonamy tą metodę z dodanym Bearerem - to doda się paramter Mode:"User Zalogowany" do Order i z accounts-service pobierze się ID zalogowanego użytkownika i też doda się do zamówienia
- odejmują się stany magazynowe z products-service
- dodają sie orderProducts powiązane z tym zamówieniem - duplikaty obiektów w aktualnym stanie z products-service

*Niestety nie ma tam więcej fieldsów typu adres itp, ale sam fakt złozenia jest :)*

reszta endpointów typu update i delete też działą - jeśli jest w swaggerze.

# Cypress - testy aplikacji
Do uruchomienia - w obrębie głównego folderu:
1) pobrać potrzebne paczki - npm install
2) uruchomić cypress - npm run open

Accounts-service/Accounts.spec.js - podstawowe testy serwisu accounts.\
Products-service/Products.spec.js - podstawowe testy serwisu products.\
Baskets-Orders-service/Baskets-Orders.spec.js - podstawowe testy przepływu składania zamówienia (serwis baskets + orders).

# gatewayService - na razie nie używamy
ale zostawiam bo konfiguracja feign się może przydać
localhost:9090/proxy/account - wywołanie przykładowej metody z serwisu accounts-service\
localhost:9090/proxy/basket - wywołanie przykładowej metody z serwisu baksets-service\
localhost:9090/proxy/order - wywołanie przykładowej metody z serwisu orders-service\
localhost:9090/proxy/product - wywołanie przykładowej metody z serwisu products-service
