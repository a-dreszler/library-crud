import java.util.Optional;

class LibraryApp {

    private final LibraryDao libraryDao;

    public LibraryApp(LibraryDao libraryDao) {
        this.libraryDao = libraryDao;
    }

    void mainLoop() {
        System.out.println("Witamy w bazie danych Biblioteki Publicznej!");
        MenuOption menuOption;
        do {
            menuOption = chooseMenuOption();
            executeOption(menuOption);
        } while (menuOption != MenuOption.CLOSE);
    }

    private void executeOption(MenuOption menuOption) {
        switch (menuOption) {
            case ADD_BOOK -> addBook();
            case READ_BOOK -> readBook();
            case UPDATE_BOOK -> updateBook();
            case DELETE_BOOK -> deleteBook();
            case CLOSE -> close();
        }
    }

    private void close() {
        System.out.println("Koniec programu. Do zobaczenia :)");
    }

    private void deleteBook() {
        System.out.println("Podaj isbn książki do usunięcia");
        String isbn = DataReader.readString();
        Optional<Book> bookToDelete = libraryDao.libraryRead(isbn);
        bookToDelete.ifPresentOrElse(
                book -> {
                    System.out.println("Usuwam książkę o danych:");
                    System.out.println(book);
                    int booksDeleted = libraryDao.libraryDelete(book.getId());
                    if (booksDeleted > 0) {
                        System.out.println("Usunięto książkę");
                    } else {
                        System.out.println("Nie udało się usunąć książki");
                    }
                },
                () -> System.out.println("Nie znaleziono książki o isbn " + isbn)
        );
    }

    private void addBook() {
        String isbn = requestAndGetIsbn();
        String title = requestAndGetTitle();
        String author = requestAndGetAuthor();
        int year = requestAndGetYear();
        Book book = new Book(title, author, year, isbn);
        boolean wasSaved = libraryDao.librarySave(book);
        if (wasSaved) {
            System.out.println("Udało się zapisać książkę. Dane zapisanej książki:");
            System.out.println(book);
        } else {
            System.out.println("Nie udało się zapisać książki");
        }
    }

    private void readBook() {
        String isbn = requestAndGetIsbn();
        Optional<Book> bookOptional = libraryDao.libraryRead(isbn);
        bookOptional.ifPresentOrElse(
                book -> {
                    System.out.println("Udało się wczytać książkę. Dane książki:");
                    System.out.println(book);
                },
                () -> System.out.println("Nie udało się znaleźć książki o isbn " + isbn)
        );
    }

    private void updateBook() {
        String isbn = requestAndGetIsbn();
        Optional<Book> bookOptional = libraryDao.libraryRead(isbn);
        bookOptional.ifPresentOrElse(
                this::updateLoop,
                () -> System.out.println("Nie udało się znaleźć książki o isbn " + isbn)
        );
    }

    private void updateLoop(Book book) {
        System.out.println("Edytujesz książkę:");
        System.out.println(book);
        UpdateOption updateOption;
        Book originalBook = copyBook(book);
        do {
            updateOption = chooseUpdateOption();
            executeUpdate(originalBook, book, updateOption);
        } while (updateOption != UpdateOption.RETURN && updateOption != UpdateOption.APPLY_CHANGES);
    }

    private void executeUpdate(Book originalBook, Book book, UpdateOption updateOption) {
        switch (updateOption) {
            case UPDATE_ISBN -> {
                String isbn = requestAndGetIsbn();
                book.setIsbn(isbn);
            }
            case UPDATE_TITLE -> {
                String title = requestAndGetTitle();
                book.setTitle(title);
            }
            case UPDATE_AUTHOR -> {
                String author = requestAndGetAuthor();
                book.setAuthor(author);
            }
            case UPDATE_YEAR -> {
                int year = requestAndGetYear();
                book.setYear(year);
            }
            case APPLY_CHANGES -> applyChanges(book, originalBook);
            case RETURN -> System.out.println("Wracam do menu głównego");
        }
    }

    private void applyChanges(Book book, Book originalBook) {
        if (!book.equals(originalBook)) {
            int updatedBooks = libraryDao.libraryUpdate(book);
            if (updatedBooks > 0) {
                System.out.println("Zaktualizowano książkę.");
                System.out.println("Dane przed aktualizacją:");
                System.out.println(originalBook);
                System.out.println("Dane po aktualizacji:");
                System.out.println(book);
            } else {
                System.out.println("Nie udało się zaktualizować książki");
            }
        } else {
            System.out.println("Nie zaktualizowano książki - nie wprowadzono żadnych zmian");
        }
    }

    private Book copyBook(Book bookToCopy) {
        return new Book(
                bookToCopy.getTitle(),
                bookToCopy.getAuthor(),
                bookToCopy.getYear(),
                bookToCopy.getIsbn());
    }

    private UpdateOption chooseUpdateOption() {
        Optional<UpdateOption> updateOption;
        do {
            printUpdateOptions();
            updateOption = UpdateOption.getFromValue(DataReader.readOptionNumber());
        } while (updateOption.isEmpty());

        return updateOption.get();
    }

    private MenuOption chooseMenuOption() {
        Optional<MenuOption> menuOption;

        do {
            System.out.println("Wybierz opcję:");
            printMenuOptions();
            menuOption = MenuOption.getFromValue(DataReader.readOptionNumber());
            if (menuOption.isEmpty()) {
                System.out.println("Podano nieprawidłową opcję. Spróbuj jeszcze raz.");
            }
        } while (menuOption.isEmpty());

        return menuOption.get();
    }

    private void printMenuOptions() {
        for (MenuOption menuOption : MenuOption.values()) {
            System.out.println(menuOption);
        }
    }

    private void printUpdateOptions() {
        for (UpdateOption updateOption : UpdateOption.values()) {
            System.out.println(updateOption);
        }
    }

    private String requestAndGetIsbn() {
        System.out.println("Podaj ISBN:");
        return DataReader.readString();
    }

    private String requestAndGetTitle() {
        System.out.println("Podaj nowy tytuł:");
        return DataReader.readString();
    }

    private String requestAndGetAuthor() {
        System.out.println("Podaj autora:");
        return DataReader.readString();
    }

    private int requestAndGetYear() {
        System.out.println("Podaj rok wydania:");
        return DataReader.readYear();
    }
}