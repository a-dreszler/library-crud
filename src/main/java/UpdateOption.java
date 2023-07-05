import java.util.Optional;

enum UpdateOption {

    UPDATE_ISBN("Zmień ISBN", 1),
    UPDATE_TITLE("Zmień tytuł", 2),
    UPDATE_AUTHOR("Zmień autora", 3),
    UPDATE_YEAR("Zmień rok wydania", 4),
    APPLY_CHANGES("Zaakceptuj zmiany i zapisz", 5),
    RETURN("Powrót do menu głównego bez zapisywania zmian", 6);

    private final String descriptionPl;
    private final int value;

    UpdateOption(String descriptionPl, int value) {
        this.descriptionPl = descriptionPl;
        this.value = value;
    }

    static Optional<UpdateOption> getFromValue(int value) {
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