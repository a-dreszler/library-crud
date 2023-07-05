class Main {

    public static void main(String[] args) {
        try {
            LibraryDao libraryDao = new LibraryDao();

            LibraryApp libraryApp = new LibraryApp(libraryDao);
            libraryApp.mainLoop();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
