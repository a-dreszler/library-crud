import java.util.Optional;

enum MenuOption {

    ADD_BOOK("Dodaj książkę do bazy danych", 1),
    READ_BOOK("Pobierz informacje o książce", 2),
    UPDATE_BOOK("Zaktualizuj dane książki", 3),
    DELETE_BOOK("Usuń książkę z bazy danych", 4),
    CLOSE("Wyłącz aplikację", 5);

    private final String descriptionPl;
    private final int value;

    MenuOption(String descriptionPl, int value) {
        this.descriptionPl = descriptionPl;
        this.value = value;
    }

    static Optional<MenuOption> getFromValue(int value) {
        if (value > 0 && value <= values().length) {
            return Optional.of(values()[value - 1]);
        }

        return Optional.empty();
    }

    @Override
    public String toString() {
        return value + " - " + descriptionPl;
    }
}