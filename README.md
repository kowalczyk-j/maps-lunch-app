# BulionApp

**Autorzy**: Jakub Kowalczyk, Kinga Świderek, Magdalena Dudek

## Opis

Życie studenta to nie tylko nauka, imprezy i słoiki z domu rodzinnego. Każdy z nas lubi wyjść na dobre, a często także szybkie, jedzenie po skończeniu długiego dnia na uczelni. Lecz co robić, gdy znudził nam się ten sam bar mleczny czy pobliska pizzeria? Wtedy z pomocą przychodzi nasza aplikacja, która łączy w sobie wszystkie oferty lunchowe, dzięki czemu wiadomo, gdzie można zjeść to, na co ma się ochotę, tanio i szybko. Żadne z obecnie dostępnych na rynku rozwiązań nie oferuje przejrzystej, aktualizowanej na bieżąco mapy lunchy i ofert specjalnych. *BulionApp* nie tylko pokaże aktualne menu - pozwoli także na wyszukiwanie obiadu względem konkretnych dań czy opcji wegańskich. System opinii oraz ulubionych miejsc zapewni skuteczną pomoc w wyborze.

## Przypadki użycia

**Użytkownicy** mają dostęp do mapy z pinezkami restauracji, które oferują zweryfikowany, jakościowy lunch lub oferty specjalne w danym dniu. Za pomocą przycisku są w stanie zlokalizować siebie na mapie i zobaczyć polecane miejsca w okolicy oraz wyznaczyć do nich trasę. Mogą sortować wyniki względem konkretnych wymagań np. liczby dań, godzin trwania lunchu, oceny czy nawet wyszukiwać dania po konkretnej nazwie. Każde konto ma dostęp do zapisanych ulubionych miejsc oraz może oceniać restauracje. Istnieje także możliwość personalizacji profilu poprzez zmianę nazwy użytkownika i zdjęcia profilowego.

**Administratorzy** aplikacji zarządzają listą pokazywanych restauracji, aktualizują podstawowe informacje o restauracji oraz lunche, a w tym ich ceny, dostępność i warunki promocji. Ponadto, moderują opinie - mogą je usuwać, gdy są wulgarne lub nieadekwatne. Starają się na bieżąco dbać o najlepszy User Experience.

**Właściciele restauracji** po zweryfikowaniu będą mieli możliwość aktualizowania oferty lunchowej na prawach administratora. Opcjonalnie, mogliby również wykupić lepsze pozycjonowanie w wyszukiwaniu poprzez płatną współpracę.

## Screeny z aplikacji
![screen-mapy](https://i.postimg.cc/bv7fBgx9/Screenshot-20230725-131001-Bulion-App.jpg) ![screen-dialog-mapy](https://i.postimg.cc/Y9vfhNZZ/Screenshot-20230725-131035-Bulion-App.jpg) 
![screen-wyszukiwanie](https://i.postimg.cc/J4VczkqY/Screenshot-20230725-131419-Bulion-App.jpg) ![screen-ekran-restauracji](https://i.postimg.cc/kMtvCcxt/Screenshot-20230725-131654-Bulion-App.jpg)
![screen-profil](https://i.postimg.cc/PJZWMXtM/Screenshot-20230725-131727-Bulion-App.jpg) ![screen-ustawienia](https://i.postimg.cc/50D5vYM0/Screenshot-20230725-131740-Bulion-App.jpg)


## Założenia projektowe i możliwości rozwoju
- W ramach uproszczenia w aplikacji istnieje jeden domyślny użytkownik, a określenie jego roli (czy jest właścicielem restauracji) odbywa się poprzez switch w ustawieniach profilu. W komercyjnej wersji powinien być zaimplementowany system logowania i przydzielania odpowiednich ról danym użytkownikom.
- Większość restauracji informuje o nowej ofercie lunchowej codziennie rano, dlatego też zrezygnowaliśmy z opcji wprowadzenia lunchu na cały tydzień. Właściciele restauracji bądź administratorzy powinni edytować lunch w dniu, w którym on obowiązuje. Dzięki automatycznie pobieranej dacie i godzinie edycji, użytkownik ma pewność, że lunch jest aktualny. W przypadku, gdy restauracja z wyprzedzeniem podaje menu na cały tydzień, użytkownik ma możliwość zobaczenia tych dań poprzez odnośnik do odpowiedniego posta (przycisk Menu).
- Wpisując frazę w polu wyszukiwania użytkownik otrzymuje wyniki nie tylko restauracji z pasującą nazwą, lecz także restauracje, których oferta lunchowa pokrywa się z daną frazą. Przykładowo, wpisując "słoik", poza restauracją Słoik może wyświetlić się też inna restauracja, która w danym dniu serwuje na deser budyń w **słoik**u.
- Aplikacja jest dedykowana użytkownikom smartfonów z systemem Android i jest dostępna tylko w orientacji pionowej w trybie zarówno jasnym, jak i ciemnym.
  
## Diagram
![diagram](https://i.postimg.cc/rs8JQQcz/Bulion-App-diagram.jpg)
