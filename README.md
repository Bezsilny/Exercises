# Exercises

- Better Code Hub
[![BCH compliance](https://bettercodehub.com/edge/badge/Bezsilny/Exercises?branch=master)](https://bettercodehub.com/)

- Code Climate
<a href="https://codeclimate.com/github/Bezsilny/Exercises/maintainability"><img src="https://api.codeclimate.com/v1/badges/32e08da47f7aed37fe1c/maintainability" /></a>

- Travis CI
![Build Status](https://travis-ci.com/TestowanieJAVA2017-2018Gr3/projekt2-Bezsilny.svg?token=EWL9yKKuPFQvx3ZsNxUa&branch=master)


# Testowanie aplikacji JAVA 2017-2018
## (Maven, JUnit oraz atrapy) 


-----------------

Jesteś deweloperem piszącym fragment (bardzo uproszczonej) aplikacji (tutaj klasy **Messenger**) wysyłającej komunikaty do serwera. Twoja klasa korzysta z implementacji interfejsu **MessageService**. Zadaniem twojej aplikacji (uwaga: często spotykane w praktyce) jest m.in. ukrywanie statusów oraz wyjątków generowanych przez wykorzystywane komponenty takie jak **MessageService**. Zgodnie z życzeniem klienta twoja metoda odpowiedzialna za wysyłanie komunikatów ma zwracać liczby:
- 0 gdy powodzenie
- 1 gdy występują problemy z wysłaniem
- 2 gdy adres serwera lub komunikat jest niewłaściwie zbudowany
Klasa **Messenger** powinna też dostarczać metodę do testowania połączenia z serwerem zwracającym
liczby:
- 0 w przypadku sukcesu
- 1 w przeciwnym przypadku
Szkielet systemu jest zaimplementowany w pliku **messenger.zip**.
Dokończ klasę **Messenger**, a następnie przy użyciu poznawanych technologii przeprowadź testy
jednostkowe w tzw. izolacji (czyli bez gotowej implementacji **MessageService**)

Wystepujące elementy:
- Konfiguracja TravisCi.
- Uwzględnienie powyższych wymagań.
- Przypadki testowe (uwzględniające wyjątki).
- Przetestowanie przy użyciu ręcznie stworzonych atrap 
- Przetestowanie przy użyciu Mockito
- Przetestowanie przy użyciu EasyMock
- Pokrycie kodu 100% (w przypadku ręcznie stworzonych atrap).

- Wynik z portalu BetterCodeHub.
- Integracja repozytorium z serwisem Code Climate.
- Użycie JUnit5.


--------------------------------------------------


