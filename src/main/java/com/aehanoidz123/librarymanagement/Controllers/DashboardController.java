package com.aehanoidz123.librarymanagement.Controllers;

import com.aehanoidz123.librarymanagement.Database.Database;
import com.aehanoidz123.librarymanagement.Entities.Book;
import com.aehanoidz123.librarymanagement.Entities.IssueBook;
import com.aehanoidz123.librarymanagement.Entities.Student;
import com.aehanoidz123.librarymanagement.Entities.User;
import com.aehanoidz123.librarymanagement.LibraryManagementApplication;
import com.aehanoidz123.librarymanagement.Services.*;
import com.google.zxing.WriterException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class DashboardController implements Initializable {

    private String ggimagelink;
    @FXML
    private ImageView bookDetailImage;

    @FXML
    private TableColumn<Book, String> booksAuthorColumn;

    @FXML
    private TableColumn<Book, String> booksAvailabilityColumn;

    @FXML
    private Button booksBtn;

    @FXML
    private TableColumn<Book, String> booksCategoryColumn;

    @FXML
    private TableColumn<Book, String> booksISBNColumn;

    @FXML
    private TableColumn<Book, String> booksIdColumn;

    @FXML
    private TableColumn<Book, Integer> booksQuantityColumn;

    @FXML
    private TableColumn<Book, Integer> booksRemainningColumn;

    @FXML
    private TableView<Book> booksTableView;

    @FXML
    private TableColumn<Book, String> booksTitleColumn;

    @FXML
    private Label detailAuthor;

    @FXML
    private Label detailCategory;

    @FXML
    private Label detailTitle;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button homeBtn;

    @FXML
    private AnchorPane homeCenterPane;

    @FXML
    private AnchorPane homeRightPane;

    @FXML
    private Button issueBtn;

    @FXML
    private Button commentBtn;

    @FXML
    private Button logout;

    @FXML
    private Button studentsBtn;

    @FXML
    private Label username;

    @FXML
    private AnchorPane booksCenterPane;

    @FXML
    private AnchorPane booksRightPane;

    @FXML
    private ProgressIndicator loadingProgress;

    @FXML
    private AnchorPane loadingPane;

    @FXML
    private StackPane stackPane;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField crudAuthor;

    @FXML
    private TextField crudCategory;

    @FXML
    private TextField crudISBN;

    @FXML
    private TextField crudQuantity;

    @FXML
    private TextField crudRemaining;

    @FXML
    private TextField crudTitle;

    @FXML
    private ImageView crudImageView;

    @FXML
    private StackPane crudStackPane;

    @FXML
    private Button crudAddBookBtn;

    @FXML
    private TextField bookSearch;

    @FXML
    private TableColumn<Student, String> studentsEmailColumn;

    @FXML
    private TableColumn<Student, Integer> studentsIdColumn;

    @FXML
    private TableColumn<Student, String> studentsNameColumn;

    @FXML
    private TableColumn<Student, String> studentsPhoneColumn;

    @FXML
    private TableColumn<Student, String> studentsStudentIdColumn;

    @FXML
    private TableView<Student> studentsTableView;

    @FXML
    private AnchorPane studentsCenterPane;

    @FXML
    private AnchorPane studentsRightPane;

    @FXML
    private TextField crudStudentId;

    @FXML
    private TextField crudStudentName;

    @FXML
    private TextField crudStudentPhone;

    @FXML
    private TextField crudStudentEmail;

    @FXML
    private Button crudAddStudentBtn;

    @FXML
    private TextField studentSearch;

    @FXML
    private TableColumn<IssueBook, Date> issueBookDateBorrowedColumn;

    @FXML
    private TableColumn<IssueBook, Date> issueBookDateReturnedColumn;

    @FXML
    private TableColumn<IssueBook, String> issueBookISBNColumn;

    @FXML
    private TableColumn<IssueBook, Integer> issueBookIdColumn;

    @FXML
    private TableColumn<IssueBook, Double> issueBookLateFeeColumn;

    @FXML
    private TableColumn<IssueBook, String> issueBookStudentIdColumn;

    @FXML
    private TableColumn<IssueBook, String> issueBookStudentNameColumn;

    @FXML
    private TableColumn<IssueBook, String> issueBookTitleColumn;

    @FXML
    private AnchorPane issueBooksCenterPane;

    @FXML
    private AnchorPane issueBooksRightPane;

    @FXML
    private Button issueBooksBtn;

    @FXML
    private TableView<IssueBook> issueBooksTableView;

    @FXML
    private TextField issueBookStudentIdTextField;

    @FXML
    private TextField issueBookISBNTextField;

    @FXML
    private AnchorPane studentDetails;

    @FXML
    private Label studentDetailStudentId;

    @FXML
    private Label studentDetailName;

    @FXML
    private Label studentDetailEmail;

    @FXML
    private Label studentDetailPhone;

    @FXML
    private AnchorPane bookDetails;

    @FXML
    private Label bookDetailsTitle;

    @FXML
    private Label bookDetailsAuthor;

    @FXML
    private Label bookDetailsISBN;

    @FXML
    private Label bookDetailsCategory;

    @FXML
    private Label bookDetailsAvailability;

    @FXML
    private Button studentDetailBtn;

    @FXML
    private Button bookDetailsBtn;

    @FXML
    private AnchorPane issueForm;

    @FXML
    private Label issueFormStudentId;

    @FXML
    private Label issueFormStudentName;

    @FXML
    private Label issueFormStudentPhone;

    @FXML
    private Label issueFormStudentEmail;

    @FXML
    private Label issueFormBookTitle;

    @FXML
    private Label issueFormBookAuthor;

    @FXML
    private Label issueFormBookISBN;

    @FXML
    private Label issueFormBookCategory;

    @FXML
    private ImageView issueFormBookImageView;

    @FXML
    private ImageView bookDetailsImageView;

    @FXML
    private DatePicker issueFormDateBorrowed;

    @FXML
    private DatePicker issueFormDateReturned;

    @FXML
    private TextField issueBookSearch;

    @FXML
    private Button returnBookBtn;

    @FXML
    private AnchorPane returnBookCenterPane;

    @FXML
    private AnchorPane returnBookRightPane;

    @FXML
    private TextField returnBookSearch;

    @FXML
    private Label returnBookIssueId;

    @FXML
    private Label returnBookStudentId;

    @FXML
    private Label returnBookStudentName;

    @FXML
    private Label returnBookStudentPhone;

    @FXML
    private Label returnBookStudentEmail;

    @FXML
    private Label returnBookBookTitle;

    @FXML
    private Label returnBookBookAuthor;

    @FXML
    private Label returnBookBookISBN;

    @FXML
    private Label returnBookBookCategory;

    @FXML
    private Label returnBookOverdue;

    @FXML
    private AnchorPane returnBookForm;

    @FXML
    private Label returnBookIssuedDate;

    @FXML
    private Label returnBookTotalFee;

    @FXML
    private ImageView returnBookBookCover;

    @FXML
    private Label rateLabel = new Label();

    @FXML
    private Button generateQRBtn;

    @FXML
    private Button returnBookFromIssuePaneBtn;

    @FXML
    private Button previousPageBtn;

    @FXML
    private Button nextPageBtn;

    private ObservableList<Book> books;
    private ObservableList<Student> students;
    private ObservableList<IssueBook> issueBooks;
    private ObservableList<Book> filteredData;

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;
    private String getBookIsbnComment;

    private String CategoryHistory;
    private String UserIDHistory;
    private String suggestStudentName;
    private String suggestStudentID;
    @FXML
    private Button suggestBtn;

    private final GoogleBooksService googlebooksService = new GoogleBooksService();

    private int localCurPage;
    private int totalBooks;
    private int totalPages;
    @FXML
    private Button reviewBtn;

    public void SuggestBook(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(LibraryManagementApplication.class.getResource("Suggest-view.fxml"));
            Scene suggestScene = new Scene(loader.load());
            SuggestViewController suggestViewController = loader.getController();
            suggestViewController.setStudentname(suggestStudentName);
            suggestViewController.setStudentID(suggestStudentID);
            Stage stage = new Stage();
            stage.setScene(suggestScene);
            stage.setTitle("Suggested Book");
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void Review(String bookisbn){
        try {
            FXMLLoader loader =
                    new FXMLLoader(LibraryManagementApplication.class.getResource("Review.fxml"));
            Scene reviewScene = new Scene(loader.load());
            reviewScene.getStylesheets().add(LibraryManagementApplication.class.getResource("css/mainStyle.css").toExternalForm());
            ReviewController reviewController = loader.getController();
            reviewController.setIsbn(bookisbn);
            reviewController.setUserID(UserIDHistory);
            reviewController.setCategory(CategoryHistory);
            Stage stage = new Stage();
            stage.setScene(reviewScene);
            stage.setTitle("Library Management");
            Image image = new Image(String.valueOf(LibraryManagementApplication.class.getResource("icons/book_icon.png")));
            stage.getIcons().add(image);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION);
            logoutAlert.setTitle("Logout");
            logoutAlert.setHeaderText("Are you sure you want to logout?");
            logoutAlert.setContentText("You will be logged out and all your changes will be lost.");
            ButtonType typeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType typeCancel = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
            logoutAlert.getButtonTypes().setAll(typeOk, typeCancel);
            Optional<ButtonType> result = logoutAlert.showAndWait();
            if (result.get() == typeOk) {
                FXMLLoader loader = new FXMLLoader(LibraryManagementApplication.class.getResource("login-view.fxml"));
                Stage currentStage = (Stage) logout.getScene().getWindow();
                currentStage.close();
                Stage stage = new Stage();
                Scene loginScene = new Scene(loader.load());
                loginScene.getStylesheets().add(LibraryManagementApplication.class.getResource("css/loginStyle.css").toExternalForm());
                stage.setScene(loginScene);
                stage.setTitle("Library Management");
                Image image = new Image(String.valueOf(LibraryManagementApplication.class.getResource("icons/book_icon.png")));
                stage.getIcons().add(image);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateQRCode(ActionEvent event)  {
        loading();
        String studentId = SelectedStudentDetailsService.studentId;
        String studentName = SelectedStudentDetailsService.studentName;
        String studentPhone = SelectedStudentDetailsService.studentPhone;
        String studentEmail = SelectedStudentDetailsService.studentEmail;

        Task<Void> generate = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                GenerateQRCodeService.generateQRCode(studentId, studentName, studentPhone, studentEmail);
                return null;
            }
        };

        Task<Void> sendEmail = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                SendingEmailService.sendEmail(studentEmail, studentId);
                return null;
            }
        };
        generate.run();
        sendEmail.run();
        generate.setOnFailed(e -> {
            loaded();
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText("Failed to generate QR code");
                alert.setContentText("Cannot create QR code for student name: " + studentName);
                alert.show();
            });
        });
        sendEmail.setOnFailed(e -> {
            loaded();
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText("Failed to send email");
                alert.setContentText("Cannot send email to student email: " + studentEmail);
                alert.show();
            });
        });
        sendEmail.setOnSucceeded(e -> {
            loaded();
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success message");
                alert.setHeaderText("QR code and email sent successfully");
                alert.setContentText("QR code and email sent to student email: " + studentEmail);
                alert.show();
            });
        });
    }

    public void scanQRCode() throws IOException {
        FXMLLoader loader = new FXMLLoader(LibraryManagementApplication.class.getResource("webcam-view.fxml"));
        Scene mainScene = new Scene(loader.load());
        mainScene.getStylesheets().add(LibraryManagementApplication.class.getResource("css/mainStyle.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(mainScene);
        stage.setTitle("Library Management");
        Image image = new Image(String.valueOf(LibraryManagementApplication.class.getResource("icons/book_icon.png")));
        stage.getIcons().add(image);
        stage.show();
    }

    public List<Book> getAllBooks(int curPage, int limit) throws SQLException {
        List<Book> books = new ArrayList<>();
        connection = Database.connect();
        String sql = "SELECT * FROM Book ORDER BY title ASC LIMIT ? OFFSET ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, limit);
        statement.setInt(2, (curPage - 1) * 12);
        result = statement.executeQuery();
        while (result.next()) {
            Book myBook = new Book();
            myBook.setId(result.getString("id"));
            myBook.setTitle(result.getString("title"));
            myBook.setAuthor(result.getString("author"));
            myBook.setISBN(result.getString("isbn"));
            myBook.setSrc(result.getString("src"));
            myBook.setCategory(result.getString("category"));
            myBook.setQuantity(result.getInt("quantity"));
            myBook.setRemaining(result.getInt("remaining"));
            myBook.setAvailability(result.getString("availability"));
            myBook.setGgimagesrc(result.getString("ggimagesrc"));
            myBook.setRate(result.getFloat("rate"));
            books.add(myBook);
        }
        return books;
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        connection = Database.connect();
        String sql = "SELECT * FROM Book";
        statement = connection.prepareStatement(sql);
        result = statement.executeQuery();
        while (result.next()) {
            Book myBook = new Book();
            myBook.setId(result.getString("id"));
            myBook.setTitle(result.getString("title"));
            myBook.setAuthor(result.getString("author"));
            myBook.setISBN(result.getString("isbn"));
            myBook.setSrc(result.getString("src"));
            myBook.setCategory(result.getString("category"));
            myBook.setQuantity(result.getInt("quantity"));
            myBook.setRemaining(result.getInt("remaining"));
            myBook.setAvailability(result.getString("availability"));
            myBook.setGgimagesrc(result.getString("ggimagesrc"));
            myBook.setRate(result.getFloat("rate"));
            books.add(myBook);
        }
        return books;
    }

    private void calculateTotalPages() {
        try {
            totalBooks = getAllBooks().size();
            totalPages = (int) Math.ceil((double) totalBooks / 10);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBookDetails(Book book) {
        getBookIsbnComment = book.getISBN();
        String imagePath = book.getSrc();
        Image image;
        if (Objects.equals(imagePath, "images/null.null")) {
            String ggImage = book.getGgimagesrc();
            image = new Image(ggImage);
        }
        else {
            image = new Image(new File(imagePath).toURI().toString());
        }
        bookDetailImage.setImage(image);
        detailTitle.setText(book.getTitle());
        detailAuthor.setText(book.getAuthor());
        detailCategory.setText(book.getCategory());

        String rateTXT = null;
        if (book.getRate() == 0) {
            rateTXT = "This book hasn't been rated yet";
        }
        else {
            double point = book.getRate();
            DecimalFormat df = new DecimalFormat("#.#");
            String formatted = df.format(point);
            rateTXT = String.valueOf(formatted) + "/5";
        }
        rateLabel.setText(rateTXT);
        if (book.getRemaining() <= 0) {
            issueBtn.setDisable(true);
        }
    }

    public void setSelectedBtn(Button btn) {
        btn.getStyleClass().add("selected");
    }

    public void setUnselectedBtn(Button btn) {
        btn.getStyleClass().remove("selected");
    }

    public void loading() {
        loadingProgress.setVisible(true);
        loadingPane.setVisible(true);
        loadingPane.setDisable(false);
        stackPane.setOpacity(0.7);
    }

    public void loaded() {
        loadingProgress.setVisible(false);
        loadingPane.setVisible(false);
        loadingPane.setDisable(true);
        stackPane.setOpacity(1);
    }

    public void handleHomePane(int curPage, int limit, boolean hasPreviousPage, boolean hasNextPage) {
        loading();
        localCurPage = curPage;
        setUnselectedBtn(homeBtn);
        Task<List<Book>> task = new Task<List<Book>>() {
            @Override
            protected List<Book> call() throws Exception {
                return getAllBooks(curPage, limit);
            }
        };
        task.setOnSucceeded(e1 -> {
            loaded();
            setSelectedBtn(homeBtn);
            List<Book> books = task.getValue();
            if (!hasPreviousPage && hasNextPage) {
                previousPageBtn.setDisable(true);
                nextPageBtn.setDisable(false);
            } else if (hasPreviousPage && !hasNextPage) {
                previousPageBtn.setDisable(false);
                nextPageBtn.setDisable(true);
            } else if (hasNextPage && hasPreviousPage) {
                previousPageBtn.setDisable(false);
                nextPageBtn.setDisable(false);
            }
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                homeCenterPane.setVisible(true);
                homeRightPane.setVisible(true);
                gridPane.getChildren().clear();
                if (!books.isEmpty()) {
                    setBookDetails(books.get(0));
                }
                try {
                    int column = 0;
                    int row = 1;
                    for (int i = 0; i < books.size(); i++) {
                        FXMLLoader loader = new FXMLLoader(LibraryManagementApplication.class.getResource("item-view.fxml"));
                        AnchorPane anchorPane = loader.load();
                        ItemController itemController = loader.getController();
                        itemController.setItem(books.get(i));
                        if (column == 3) {
                            column = 0;
                            row++;
                        }
                        gridPane.add(anchorPane, column++, row);
                        GridPane.setMargin(anchorPane, new Insets(5));
                        // set click listener
                        int finalI = i;
                        anchorPane.setOnMouseClicked(e2 -> {
                            setBookDetails(books.get(finalI));
                        });
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        });
        task.setOnFailed(event -> {
            loaded();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to retrieve books.");
            alert.setContentText(event.getSource().getException().getMessage());
            alert.showAndWait();
        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void goToPreviousPage(ActionEvent event) {
        if (localCurPage - 1 == 1) {
            handleHomePane(--localCurPage, 12, false, true);
        } else {
            handleHomePane(--localCurPage, 12, true, true);
        }
    }

    public void goToNextPage(ActionEvent event) throws SQLException {
        if (localCurPage + 2 < totalPages) {
            handleHomePane(++localCurPage, 12, true, true);
        } else {
            handleHomePane(++localCurPage, 12, true, false);
        }
    }

    public void handleBooksCenterPane() {
        loading();
        Task<ObservableList<Book>> task = new Task<ObservableList<Book>>() {
            @Override
            protected ObservableList<Book> call() throws Exception {
                return FXCollections.observableArrayList(getAllBooks());
            }
        };
        task.setOnSucceeded(e1 -> {
            loaded();
            books = task.getValue();
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                booksRightPane.setVisible(true);
                booksCenterPane.setVisible(true);
                booksIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                booksTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
                booksAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
                booksISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
                booksCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
                booksQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                booksRemainningColumn.setCellValueFactory(new PropertyValueFactory<>("remaining"));
                booksAvailabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
                booksTableView.setItems(books);
            });

            // search engine
            bookSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                String keyWord = newValue.toLowerCase();
                if (keyWord.isEmpty()) {
                    if (filteredData != null) {
                        filteredData = null;
                    }
                    booksTableView.setItems(books);
                    return;
                }
                filteredData = FXCollections.observableArrayList(
                        books.stream().filter(myBook ->
                                myBook.getTitle().toLowerCase().contains(keyWord) ||
                                        myBook.getAuthor().contains(keyWord) ||
                                        myBook.getCategory().contains(keyWord)
                        ).collect(Collectors.toList())
                );
                if(filteredData.isEmpty()) {
                    // Google book service
                    keyWord.replace(" ", "_");
                    Task<ObservableList<Book>> googleBooksTask = new Task<>() {
                        @Override
                        protected ObservableList<Book> call() {
                            return googlebooksService.searchBooks(keyWord);
                        }
                    };
                    googleBooksTask.setOnSucceeded(event -> {
                        ObservableList<Book> allResults = FXCollections.observableArrayList(googleBooksTask.getValue());
                        Platform.setImplicitExit(false);
                        Platform.runLater(() -> {
                            booksTableView.setItems(allResults);
                        });
                    });
                    googleBooksTask.setOnFailed(event -> {
                        Platform.setImplicitExit(false);
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Failed to retrieve books.");
                            alert.setContentText(event.getSource().getException().getMessage());
                            alert.showAndWait();
                        });
                    });
                    Thread googleBooksThread = new Thread(googleBooksTask);
                    googleBooksThread.setDaemon(true);
                    googleBooksThread.start();
                }
                else {
                    booksTableView.setItems(filteredData);
                }
            });
        });
        task.setOnFailed(e1 -> {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to retrieve books.");
                alert.setContentText(e1.getSource().getException().getMessage());
                alert.showAndWait();
            });
        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void handleBooksRightPane() {
        ObservableList<String> choiceBoxItems = FXCollections.observableArrayList(
                "Available", "Unavailable");
        choiceBox.setItems(choiceBoxItems);
    }

    public void handleSelectedBook(MouseEvent mouseEvent) {
        Book selectedBook = booksTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            crudTitle.setText(selectedBook.getTitle());
            crudAuthor.setText(selectedBook.getAuthor());
            crudISBN.setText(selectedBook.getISBN());
            crudCategory.setText(selectedBook.getCategory());
            crudQuantity.setText(String.valueOf(selectedBook.getQuantity()));
            crudRemaining.setText(String.valueOf(selectedBook.getRemaining()));
            choiceBox.setValue(selectedBook.getAvailability());
            String imagePath = selectedBook.getSrc();
            Image image;
            if (selectedBook.getSrc() == null || selectedBook.getSrc().equals("images/null.null")) {
                if (selectedBook.getSrc() == null) {
                    GoogleImageSrcService.googleThumbnail = selectedBook.getGgimagesrc();
                }
                image = new Image(selectedBook.getGgimagesrc());
            } else {
                image = new Image(new File(imagePath).toURI().toString());
            }

            crudImageView.setImage(image);
            crudImageView.setVisible(true);
//            crudAddBookBtn.setDisable(true);
            if (filteredData == null) {
                crudAddBookBtn.setDisable(true);
            } else {
                crudAddBookBtn.setDisable(false);
            }
        }
    }

    @FXML
    public void handleCommentButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(LibraryManagementApplication.class.getResource("show-comment.fxml"));
            Scene newscene = new Scene(loader.load());
            ShowCommentController controller = loader.getController();
            controller.setIsbn(getBookIsbnComment);
            Stage newstage = new Stage();
            newstage.setTitle("Feedback");
            newstage.setScene(newscene);
            newstage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addBook(ActionEvent event) throws IOException {
        if (crudTitle.getText().isEmpty()
                || crudAuthor.getText().isEmpty()
                || crudISBN.getText().isEmpty()
                || crudCategory.getText().isEmpty()
                || crudQuantity.getText().isEmpty()
                || crudRemaining.getText().isEmpty()
                || choiceBox.getSelectionModel().selectedItemProperty() == null
                || crudImageView.getImage() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            loading();
            Task<Boolean> checkISBN = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    connection = Database.connect();
                    String sql = "SELECT COUNT(*) FROM Book WHERE isbn = " + crudISBN.getText();
                    statement = connection.prepareStatement(sql);
                    result = statement.executeQuery();
                    result.next();
                    int count = result.getInt(1);
                    return count > 0;
                }
            };

            Task<Void> addBookTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        connection = Database.connect();
                        String sql = "INSERT INTO Book (title, author, isbn, src, category, quantity, remaining, availability, ggimagesrc, rate)"
                                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, crudTitle.getText());
                        statement.setString(2, crudAuthor.getText());
                        statement.setString(3, crudISBN.getText());
                        statement.setString(4, "images/" +
                                    FileNameService.fileName + "." + FileNameService.extensionName);
                        statement.setString(5, crudCategory.getText());
                        statement.setInt(6, Integer.parseInt(crudQuantity.getText()));
                        statement.setInt(7, Integer.parseInt(crudRemaining.getText()));
                        statement.setString(8, choiceBox.getValue());
                        if (GoogleImageSrcService.googleThumbnail == null) {
                            statement.setString(9, "null");
                        } else {
                            statement.setString(9, GoogleImageSrcService.googleThumbnail);
                        }
                        statement.setDouble(10, 0.0);
                        statement.executeUpdate();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                    return null;
                }
            };
            checkISBN.setOnSucceeded(event1 -> {
                if (!checkISBN.getValue()) {
                    Thread thread1 = new Thread(addBookTask);
                    thread1.setDaemon(true);
                    thread1.start();
                } else {
                    Platform.setImplicitExit(false);
                    Platform.runLater(() -> {
                        loaded();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error message");
                        alert.setHeaderText(null);
                        alert.setContentText("Book with ISBN = " + crudISBN.getText() + " already exists");
                        alert.showAndWait();
                    });
                }
            });
            checkISBN.setOnFailed(event1 -> {
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    loaded();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to check ISBN");
                    alert.showAndWait();
                });
            });
            addBookTask.setOnSucceeded(event1 -> {
                loaded();
                clearBookInput();
                Book newBook = new Book();
                newBook.setTitle(crudTitle.getText());
                newBook.setAuthor(crudAuthor.getText());
                newBook.setISBN(crudISBN.getText());
                newBook.setCategory(crudCategory.getText());
                if (!crudQuantity.getText().isEmpty() && crudQuantity.getText().matches("\\d+")) {
                    newBook.setQuantity(Integer.parseInt(crudQuantity.getText()));
                } else {
                    newBook.setQuantity(0);
                }
                if (!crudRemaining.getText().isEmpty() && crudRemaining.getText().matches("\\d+")) {
                    newBook.setQuantity(Integer.parseInt(crudRemaining.getText()));
                } else {
                    newBook.setRemaining(0);
                }
                newBook.setAvailability(choiceBox.getValue());
                newBook.setSrc("images/" + FileNameService.fileName);
                newBook.setGgimagesrc(GoogleImageSrcService.googleThumbnail);
                books.add(newBook);
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText("Information message");
                    alert.setContentText("Book added successfully");
                    alert.showAndWait();
                    handleBooksCenterPane();
                });
                clearBookInput();
                bookSearch.clear();
                totalBooks++;
                totalPages = (int) Math.ceil((double) totalBooks / 10);
            });
            addBookTask.setOnFailed(event1 -> {
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    loaded();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to add book");
                    alert.showAndWait();
                });
            });
            Thread thread = new Thread(checkISBN);
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void updateBook(ActionEvent event) {
        if (crudTitle.getText().isEmpty()
                || crudISBN.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            Task<Void> updateTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    connection = Database.connect();
                    String sql = "UPDATE Book " +
                            "SET title =?, author =?, category =?, quantity =?, src =?, remaining =?, availability =? WHERE isbn =?";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, crudTitle.getText());
                    statement.setString(2, crudAuthor.getText());
                    statement.setString(3, crudCategory.getText());

                    statement.setInt(4, Integer.parseInt(crudQuantity.getText()));
                    // set link image
                    Book selectedBook = booksTableView.getSelectionModel().getSelectedItem();

                    if (FileNameService.fileName == null) {
                        FileNameService.fileName = selectedBook.getTitle().replaceAll("\\s+", "");
                    }
                    if (FileNameService.extensionName == null) {
                        int i = selectedBook.getSrc().lastIndexOf('.');
                        FileNameService.extensionName = selectedBook.getSrc().substring(i + 1);
                    }
                    statement.setString(5, "images/" +
                            FileNameService.fileName + "." + FileNameService.extensionName);

                    statement.setInt(6, Integer.parseInt(crudRemaining.getText()));
                    statement.setString(8, crudISBN.getText());
                    statement.setString(7, choiceBox.getValue());
                    statement.executeUpdate();
                    return null;
                }
            };
            updateTask.setOnSucceeded(event1 -> {
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    loaded();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText("Information message");
                    alert.setContentText("Book updated successfully");
                    alert.showAndWait();
                    handleBooksCenterPane();
                    clearBookInput();
                });
            });
            updateTask.setOnFailed(event1 -> {
                loaded();
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to update book");
                    alert.showAndWait();
                });
            });
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation message");
            alert.setHeaderText("Confirmation message");
            alert.setContentText("Are you sure you want to update book with isbn = " + crudISBN.getText());
            ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOK) {
                loading();
                Thread thread = new Thread(updateTask);
                thread.setDaemon(true);
                thread.start();
            } else {
                Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
                cancelAlert.setTitle("Information message");
                cancelAlert.setHeaderText("Information message");
                cancelAlert.setContentText("Book update cancelled");
                cancelAlert.showAndWait();
            }
        }
    }

    public void deleteBook(ActionEvent event) {
        if (crudISBN.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            Task<Void> deleteTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    connection = Database.connect();
                    String sql = "UPDATE Book SET quantity =? remaining=? availability=? WHERE isbn =?";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, 0);
                    statement.setInt(2, 0);
                    statement.setString(3, "Unvailable");
                    statement.setString(4, crudISBN.getText());
                    statement.executeUpdate();
                    return null;
                }
            };
            deleteTask.setOnSucceeded(event1 -> {
                loaded();
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText("Information message");
                    alert.setContentText("Book delete successfully");
                    alert.showAndWait();
                    handleBooksCenterPane();
                    clearBookInput();
                });
            });
            deleteTask.setOnFailed(event1 -> {
                loaded();
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to delete book");
                    alert.showAndWait();
                });
            });
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation message");
            alert.setHeaderText("Confirmation message");
            alert.setContentText("Are you sure you want to delete book with isbn = " + crudISBN.getText());
            ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOK) {
                loading();
                Thread thread = new Thread(deleteTask);
                thread.setDaemon(true);
                thread.start();
            } else {
                Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
                cancelAlert.setTitle("Information message");
                cancelAlert.setHeaderText("Information message");
                cancelAlert.setContentText("Book delete cancelled");
                cancelAlert.showAndWait();
            }
        }
    }

    public void chooseImage() throws IOException {
        if (crudTitle.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter book title before choosing an image");
            alert.showAndWait();
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // get file name
            String fileName = crudTitle.getText().replaceAll("\\s+", "");
            FileNameService.fileName = fileName;
            // get file extension
            int i = selectedFile.getName().lastIndexOf('.');
            String extension = selectedFile.getName().substring(i + 1);
            FileNameService.extensionName = extension;
            // move file to new location
            String workDir = System.getProperty("user.dir") + File.separator + "images";
            File destDir = new File(workDir);
            if (!destDir.exists()) {
                destDir.mkdir();
            }
            File destFile = new File(destDir, fileName + "." + extension);
            Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            // set image view
            String imagePath = "images/" + fileName + "." + extension;
            Image image = new Image(new File(imagePath).toURI().toString());
            crudImageView.setImage(image);
            crudImageView.setVisible(true);
            fitParent();
        }
    }

    public List<Student> getAllStudent() throws SQLException {
        List<Student> students = new ArrayList<>();
        connection = Database.connect();
        String sql = "SELECT * FROM Student";
        statement = connection.prepareStatement(sql);
        result = statement.executeQuery();
        while (result.next()) {
            Student myStudent = new Student();
            myStudent.setId(result.getInt("id"));
            myStudent.setStudentId(result.getString("student_id"));
            myStudent.setName(result.getString("name"));
            myStudent.setPhone(result.getString("phone"));
            myStudent.setEmail(result.getString("email"));
            students.add(myStudent);
        }
        return students;
    }

    public void handleStudentsCenterPane() {
        loading();
        Task<ObservableList<Student>> task = new Task<ObservableList<Student>>() {
            @Override
            protected ObservableList<Student> call() throws Exception {
                return FXCollections.observableArrayList(getAllStudent());
            }
        };
        task.setOnSucceeded(event -> {
            loaded();
            students = task.getValue();
            studentsCenterPane.setVisible(true);
            studentsRightPane.setVisible(true);
            studentsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            studentsStudentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
            studentsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            studentsPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
            studentsEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            studentsTableView.setItems(students);

            // search engine
            studentSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                String keyWord = newValue.toLowerCase();
                if (keyWord.isEmpty()) {
                    studentsTableView.setItems(students);
                    return;
                }
                ObservableList<Student> filteredData = FXCollections.observableArrayList(
                        students.stream().filter(myStudent ->
                                myStudent.getStudentId().toLowerCase().replaceAll("\\s+", "").contains(keyWord.replaceAll("\\s+", "")) ||
                                        myStudent.getName().toLowerCase().replaceAll("\\s+", "").contains(keyWord.replaceAll("\\s+", "")) ||
                                        myStudent.getPhone().toLowerCase().replaceAll("\\s+", "").contains(keyWord.replaceAll("\\s+", ""))
                        ).collect(Collectors.toList())
                );
                studentsTableView.setItems(filteredData);
            });
        });
        task.setOnFailed(event -> {
            loaded();
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to retrieve students");
                alert.showAndWait();
            });
        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void addStudent(ActionEvent event) {
        if (crudStudentId.getText().isEmpty()
                || crudStudentName.getText().isEmpty()
                || crudStudentPhone.getText().isEmpty()
                || crudStudentEmail.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            loading();
            Task<Boolean> checkStudentId = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    connection = Database.connect();
                    String sql = "SELECT COUNT(*) FROM Student WHERE student_id = " + crudStudentId.getText();
                    statement = connection.prepareStatement(sql);
                    result = statement.executeQuery();
                    result.next();
                    int count = result.getInt(1);
                    return count > 0;
                }
            };

            Task<Void> addStudentTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        connection = Database.connect();
                        String sql = "INSERT INTO Student (student_id, name, phone, email) VALUES (?,?,?,?)";
                        statement = connection.prepareStatement(sql);
                        statement.setString(1, crudStudentId.getText());
                        statement.setString(2, crudStudentName.getText());
                        statement.setString(3, crudStudentPhone.getText());
                        statement.setString(4, crudStudentEmail.getText());
                        statement.executeUpdate();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                    return null;
                }
            };
            checkStudentId.setOnSucceeded(event1 -> {
                if (!checkStudentId.getValue()) {
                    Thread thread1 = new Thread(addStudentTask);
                    thread1.setDaemon(true);
                    thread1.start();
                } else {
                    loaded();
                    Platform.setImplicitExit(false);
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error message");
                        alert.setHeaderText(null);
                        alert.setContentText("Student with studentId = " + crudStudentId.getText() + " already exists");
                        alert.showAndWait();
                    });
                }
            });
            checkStudentId.setOnFailed(event1 -> {
                loaded();
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to check studentId");
                    alert.showAndWait();
                });
            });
            addStudentTask.setOnSucceeded(event1 -> {
                loaded();

                Student newStudent = new Student();
                newStudent.setStudentId(crudStudentId.getText());
                newStudent.setName(crudStudentName.getText());
                newStudent.setPhone(crudStudentPhone.getText());
                newStudent.setEmail(crudStudentEmail.getText());
                clearStudentInput();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information message");
                alert.setHeaderText("Information message");
                alert.setContentText("Student added successfully");
                alert.showAndWait();
                students.add(newStudent);
                handleStudentsCenterPane();
                clearStudentInput();
            });
            addStudentTask.setOnFailed(event1 -> {
                loaded();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add student");
                alert.showAndWait();
            });
            Thread thread = new Thread(checkStudentId);
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void updateStudent(ActionEvent event) {
        if (crudStudentId.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            Task<Void> updateTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    connection = Database.connect();
                    String sql = "UPDATE Student " +
                            "SET name =?, phone =?, email =? WHERE student_id =?";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, crudStudentName.getText());
                    statement.setString(2, crudStudentPhone.getText());
                    statement.setString(3, crudStudentEmail.getText());
                    statement.setInt(4, Integer.parseInt(crudStudentId.getText()));
                    statement.executeUpdate();
                    return null;
                }
            };
            updateTask.setOnSucceeded(event1 -> {
                loaded();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information message");
                alert.setHeaderText("Information message");
                alert.setContentText("Student updated successfully");
                alert.showAndWait();
                handleStudentsCenterPane();
                clearStudentInput();
            });
            updateTask.setOnFailed(event1 -> {
                loaded();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update student");
                alert.showAndWait();
            });
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation message");
            alert.setHeaderText("Confirmation message");
            alert.setContentText("Are you sure you want to update student with studentId = " + crudStudentId.getText());
            ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOK) {
                loading();
                Thread thread = new Thread(updateTask);
                thread.setDaemon(true);
                thread.start();
            } else {
                Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
                cancelAlert.setTitle("Information message");
                cancelAlert.setHeaderText("Information message");
                cancelAlert.setContentText("Student update cancelled");
                cancelAlert.showAndWait();
            }
        }
    }

    public void deleteStudent(ActionEvent event) {
        if (crudStudentId.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            Task<Void> deleteTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    connection = Database.connect();
                    String sql = "DELETE FROM Student WHERE student_id =?";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, crudStudentId.getText());
                    statement.executeUpdate();
                    return null;
                }
            };
            deleteTask.setOnSucceeded(event1 -> {
                loaded();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information message");
                alert.setHeaderText("Information message");
                alert.setContentText("Student delete successfully");
                alert.showAndWait();
                handleStudentsCenterPane();
                clearStudentInput();
            });
            deleteTask.setOnFailed(event1 -> {
                loaded();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to delete student");
                alert.showAndWait();
            });
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation message");
            alert.setHeaderText("Confirmation message");
            alert.setContentText("Are you sure you want to delete book with studentId = " + crudStudentId.getText());
            ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOK) {
                loading();
                Thread thread = new Thread(deleteTask);
                thread.setDaemon(true);
                thread.start();
            } else {
                Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
                cancelAlert.setTitle("Information message");
                cancelAlert.setHeaderText("Information message");
                cancelAlert.setContentText("Student delete cancelled");
                cancelAlert.showAndWait();
            }
        }
    }

    public void handleStudentsRightPane() {

    }

    public void handleSelectedStudent(MouseEvent mouseEvent) {
        Student selectedStudent = studentsTableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            crudStudentId.setText(selectedStudent.getStudentId());
            crudStudentName.setText(selectedStudent.getName());
            crudStudentPhone.setText(selectedStudent.getPhone());
            crudStudentEmail.setText(selectedStudent.getEmail());
            crudAddStudentBtn.setDisable(true);

            SelectedStudentDetailsService.studentId = selectedStudent.getStudentId();
            SelectedStudentDetailsService.studentEmail = selectedStudent.getEmail();
            SelectedStudentDetailsService.studentPhone = selectedStudent.getPhone();
            SelectedStudentDetailsService.studentName = selectedStudent.getName();

            generateQRBtn.setDisable(false);
        }
    }

    public void clearStudentInput() {
        crudStudentId.setText("");
        crudStudentName.setText("");
        crudStudentPhone.setText("");
        crudStudentEmail.setText("");
        crudAddStudentBtn.setDisable(false);
    }

    public void clearBookInput() {
        crudTitle.setText("");
        crudAuthor.setText("");
        crudISBN.setText("");
        crudCategory.setText("");
        crudQuantity.setText("");
        crudRemaining.setText("");
        choiceBox.setValue(null);
        crudImageView.setVisible(false);
        crudAddBookBtn.setDisable(false);
    }

    public List<IssueBook> getAllIssueBook() throws SQLException {
        List<IssueBook> issueBookList = new ArrayList<IssueBook>();
        connection = Database.connect();
        String sql = "SELECT * FROM IssuedBook";
        statement = connection.prepareStatement(sql);
        result = statement.executeQuery();
        while (result.next()) {
            IssueBook newIssueBook = new IssueBook();
            newIssueBook.setId(result.getInt(1));
            newIssueBook.setIsbn(result.getString(2));
            newIssueBook.setTitle(result.getString(3));
            newIssueBook.setStudentId(result.getString(4));
            newIssueBook.setStudentName(result.getString(5));
            newIssueBook.setDateBorrowed(result.getDate(6).toLocalDate());
            newIssueBook.setDateReturned(result.getDate(7).toLocalDate());
            newIssueBook.setLateFee(result.getDouble(8));
            issueBookList.add(newIssueBook);
        }
        return issueBookList;
    }

    public Student getStudentByStudentId(String studentId) {
        Student newStudent = new Student();
        try {
            connection = Database.connect();
            String sql = "SELECT * FROM Student WHERE student_id =?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, studentId);
            result = statement.executeQuery();
            if (result.next()) {
                newStudent.setId(result.getInt(1));
                newStudent.setStudentId(result.getString(2));
                newStudent.setName(result.getString(3));
                newStudent.setPhone(result.getString(4));
                newStudent.setEmail(result.getString(5));
            } else {
                return null;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return newStudent;
    }

    public Book getBookByISBN(String isbn){
        Book newBook = new Book();
        try {
            connection = Database.connect();
            String sql = "SELECT * FROM Book WHERE isbn =?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, isbn);
            result = statement.executeQuery();
            if (result.next()) {
                newBook.setId(result.getString(1));
                newBook.setTitle(result.getString(2));
                newBook.setAuthor(result.getString(3));
                newBook.setISBN(result.getString(4));
                newBook.setSrc(result.getString(5));
                newBook.setCategory(result.getString(6));
                newBook.setQuantity(result.getInt(7));
                newBook.setRemaining(result.getInt(8));
                newBook.setAvailability(result.getString(9));
                newBook.setGgimagesrc(result.getString(10));
            } else {
                return  null;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return newBook;
    }

    public void displayStudentDetails() {
        studentDetails.setVisible(true);
        studentDetails.setDisable(false);
    }

    public void closeStudentDetail() {
        studentDetails.setVisible(false);
        studentDetails.setDisable(true);
    }

    public void displayBookDetails() {
        bookDetails.setVisible(true);
        bookDetails.setDisable(false);
    }

    public void closeBookDetail() {
        bookDetails.setVisible(false);
        bookDetails.setDisable(true);
    }

    public void hiddenStudentDetailsBtn() {
        studentDetailBtn.setVisible(false);
        studentDetailBtn.setDisable(true);
    }

    public void displayStudentDetailBtn() {
        studentDetailBtn.setVisible(true);
        studentDetailBtn.setDisable(false);
    }

    public void hiddenBookDetailsBtn() {
        bookDetailsBtn.setVisible(false);
        bookDetailsBtn.setDisable(true);
    }

    public void displayBookDetailBtn() {
        bookDetailsBtn.setVisible(true);
        bookDetailsBtn.setDisable(false);
    }

    public void hiddenIssueForm() {
        issueForm.setVisible(false);
        issueForm.setDisable(true);
    }

    public void displayIssueForm() {
        issueForm.setVisible(true);
        issueForm.setDisable(false);
    }

    public void getStudent() {
        loading();
        hiddenStudentDetailsBtn();
        Task<Student> getStudentTask = new Task<Student>() {
            @Override
            protected Student call() throws Exception {
                String studentId = issueBookStudentIdTextField.getText();
                return getStudentByStudentId(studentId);
            }
        };
        getStudentTask.setOnSucceeded(event -> {
            loaded();
            Student myStudent = getStudentTask.getValue();
            if (myStudent != null) {
                studentDetailStudentId.setText(myStudent.getStudentId());
                studentDetailName.setText(myStudent.getName());
                studentDetailEmail.setText(myStudent.getEmail());
                studentDetailPhone.setText(myStudent.getPhone());

                StudentDetailsService.studentId = myStudent.getStudentId();
                StudentDetailsService.studentName = myStudent.getName();
                StudentDetailsService.studentEmail = myStudent.getEmail();
                StudentDetailsService.studentPhone = myStudent.getPhone();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Student Details");
                alert.setHeaderText(null);
                alert.setContentText("Student with id = " + issueBookStudentIdTextField.getText() + " loaded successfully");
                alert.showAndWait();

                displayStudentDetailBtn();
                studentDetailBtn.setText(myStudent.getName());
                suggestStudentID = issueBookStudentIdTextField.getText();
                issueBookStudentIdTextField.clear();
                suggestBtn.setDisable(false);
                suggestStudentName = myStudent.getName();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Cannot get student with id = " + issueBookStudentIdTextField.getText());
                alert.showAndWait();
                suggestBtn.setDisable(true);
            }
        });
        getStudentTask.setOnFailed(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load student details");
            alert.showAndWait();
        });
        Thread thread = new Thread(getStudentTask);
        thread.setDaemon(true);
        thread.start();
    }

    public void getBook() {
        loading();
        hiddenBookDetailsBtn();
        Task<Book> getBookTask = new Task<Book>() {
            @Override
            protected Book call() throws Exception {
                String bookISBN = issueBookISBNTextField.getText();
                return getBookByISBN(bookISBN);
            }
        };
        getBookTask.setOnSucceeded(event -> {
            loaded();
            Book myBook = getBookTask.getValue();
            if (myBook != null) {
                bookDetailsTitle.setText(myBook.getTitle());
                bookDetailsAuthor.setText(myBook.getAuthor());
                bookDetailsISBN.setText(myBook.getISBN());
                bookDetailsCategory.setText(myBook.getCategory());
                bookDetailsAvailability.setText(myBook.getAvailability());
                String imagePath = myBook.getSrc();
                Image image;
                if (Objects.equals(imagePath, "images/null.null")) {
                    image = new Image(myBook.getGgimagesrc());
                } else {
                    image = new Image(new File(imagePath).toURI().toString());
                }
                bookDetailsImageView.setImage(image);

                BookDetailsService.bookTitle = myBook.getTitle();
                BookDetailsService.bookAuthor = myBook.getAuthor();
                BookDetailsService.bookISBN = myBook.getISBN();
                BookDetailsService.bookCategory = myBook.getCategory();
                BookDetailsService.bookSrc = myBook.getSrc();
                BookDetailsService.bookAvailability = myBook.getAvailability();
                BookDetailsService.bookRemaining = myBook.getRemaining();
                BookDetailsService.bookGoogleSrc = myBook.getGgimagesrc();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Book Details");
                alert.setHeaderText(null);
                alert.setContentText("Book with ISBN = " + issueBookISBNTextField.getText() + " loaded successfully");
                alert.showAndWait();

                displayBookDetailBtn();
                bookDetailsBtn.setText(myBook.getTitle());
                issueBookISBNTextField.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Cannot get book with ISBN = " + issueBookISBNTextField.getText());
                alert.showAndWait();
            }
        });
        getBookTask.setOnFailed(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load book details");
            alert.showAndWait();
        });
        Thread thread = new Thread(getBookTask);
        thread.setDaemon(true);
        thread.start();
    }

    public void issueBook() {
        if (studentDetailBtn.isDisabled() || bookDetailsBtn.isDisabled()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please select a student and a book first");
            alert.showAndWait();
        } else {
            if (Objects.equals(BookDetailsService.bookAvailability, "Available")) {
                displayIssueForm();

                issueFormStudentId.setText(StudentDetailsService.studentId);
                issueFormStudentName.setText(StudentDetailsService.studentName);
                issueFormStudentEmail.setText(StudentDetailsService.studentEmail);
                issueFormStudentPhone.setText(StudentDetailsService.studentPhone);

                issueFormDateBorrowed.setValue(LocalDate.now());

                issueFormBookTitle.setText(BookDetailsService.bookTitle);
                issueFormBookAuthor.setText(BookDetailsService.bookAuthor);
                issueFormBookISBN.setText(BookDetailsService.bookISBN);
                issueFormBookCategory.setText(BookDetailsService.bookCategory);
                String imagePath = BookDetailsService.bookSrc;
                Image image;
                if (Objects.equals(imagePath, "images/null.null")) {
                    image = new Image(BookDetailsService.bookGoogleSrc);
                } else {
                    image = new Image(new File(imagePath).toURI().toString());
                }
                issueFormBookImageView.setImage(image);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("This book is currently unavailable");
                alert.showAndWait();
            }
        }
    }

    public void updateBookAfterIssue() {
        try {
            connection = Database.connect();
            String sql = "UPDATE Book SET availability = ?, remaining = ? WHERE isbn =" + bookDetailsISBN.getText();
            statement = connection.prepareStatement(sql);
            BookDetailsService.bookRemaining--;
            if (BookDetailsService.bookRemaining <= 0) {
                statement.setString(1, "Unavailable");
                statement.setInt(2, 0);
            } else {
                statement.setString(1, "Available");
                statement.setInt(2, BookDetailsService.bookRemaining);
            }
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateIssueBook() {
        try {
            connection = Database.connect();
            String sql = "INSERT INTO IssuedBook (isbn, title, student_id, student_name, date_borrowed, date_returned) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, BookDetailsService.bookISBN);
            preparedStatement.setString(2, BookDetailsService.bookTitle);
            preparedStatement.setString(3, StudentDetailsService.studentId);
            preparedStatement.setString(4, StudentDetailsService.studentName);
            preparedStatement.setDate(5, java.sql.Date.valueOf(BookDetailsService.bookBorrowDate));
            preparedStatement.setDate(6, java.sql.Date.valueOf(BookDetailsService.bookReturnDate));
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void confirmIssue() {
        if (issueFormDateReturned.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Please select borrow and return dates");
            alert.showAndWait();
        } else if (issueFormDateReturned.getValue().isBefore(issueFormDateBorrowed.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Return date should be after borrow date");
            alert.showAndWait();
        } else {
            loading();
            BookDetailsService.bookBorrowDate = issueFormDateBorrowed.getValue();
            BookDetailsService.bookReturnDate = issueFormDateReturned.getValue();
            Task<Void> updateBook = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateBookAfterIssue();
                    return null;
                }
            };
            Task<Void> updateIssueBook = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateIssueBook();
                    return null;
                }
            };
            updateBook.setOnSucceeded(event -> {
                Thread thread1 = new Thread(updateIssueBook);
                thread1.setDaemon(true);
                thread1.start();
            });
            updateBook.setOnFailed(event -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update book right now");
                alert.showAndWait();
            });
            updateIssueBook.setOnSucceeded(event -> {
                loaded();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Issue Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Book with ISBN = " + BookDetailsService.bookISBN + " issued to " + StudentDetailsService.studentName + " successfully");
                alert.showAndWait();

                BookDetailsService.bookISBN = "";
                BookDetailsService.bookTitle = "";
                BookDetailsService.bookAuthor = "";
                BookDetailsService.bookCategory = "";
                BookDetailsService.bookSrc = "";
                BookDetailsService.bookGoogleSrc = "";
                BookDetailsService.bookAvailability = "";
                BookDetailsService.bookRemaining = 0;
                BookDetailsService.bookBorrowDate = null;
                BookDetailsService.bookReturnDate = null;

                hiddenBookDetailsBtn();
//                hiddenStudentDetailsBtn();

                hiddenIssueForm();
                handleIssueBooksCenterPane();
            });
            updateIssueBook.setOnFailed(event -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to issue book with ISBN = " + BookDetailsService.bookISBN);
                alert.showAndWait();
            });
            Thread thread2 = new Thread(updateBook);
            thread2.setDaemon(true);
            thread2.start();
        }
    }

    public void handleIssueBooksCenterPane() {
        loading();
        Task<ObservableList<IssueBook>> task = new Task<ObservableList<IssueBook>>() {
            @Override
            protected ObservableList<IssueBook> call() throws Exception {
                return FXCollections.observableArrayList(getAllIssueBook());
            }
        };
        task.setOnSucceeded(event -> {
            loaded();
            issueBooksCenterPane.setVisible(true);
            issueBooksRightPane.setVisible(true);
            suggestBtn.setDisable(true);
            issueBooks = task.getValue();
            issueBookIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            issueBookISBNColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            issueBookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            issueBookStudentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
            issueBookStudentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
            issueBookDateBorrowedColumn.setCellValueFactory(new PropertyValueFactory<>("dateBorrowed"));
            issueBookDateReturnedColumn.setCellValueFactory(new PropertyValueFactory<>("dateReturned"));
            issueBookLateFeeColumn.setCellValueFactory(new PropertyValueFactory<>("lateFee"));
            issueBooksTableView.setItems(issueBooks);

            // search engine
            issueBookSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                String keyWord = newValue.toLowerCase();
                if (keyWord.isEmpty()) {
                    issueBooksTableView.setItems(issueBooks);
                    return;
                }
                ObservableList<IssueBook> filteredData = FXCollections.observableArrayList(
                        issueBooks.stream().filter(myIssueBook ->
                                myIssueBook.getStudentId().toLowerCase().replaceAll("\\s+", "").contains(keyWord.replaceAll("\\s+", "")) ||
                                        myIssueBook.getStudentName().toLowerCase().replaceAll("\\s+", "").contains(keyWord.replaceAll("\\s+", ""))
                        ).collect(Collectors.toList())
                );
                issueBooksTableView.setItems(filteredData);
            });
        });
        task.setOnFailed(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load issue books");
            alert.showAndWait();
        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public IssueBook getIssueBookById(int id) throws SQLException {
        IssueBook myIssueBook = new IssueBook();
        try {
            connection = Database.connect();
            String sql = "SELECT * FROM IssuedBook WHERE id = " + id;
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();
            if (!result.next()) {
                throw new Exception("Book not found");
            }
            myIssueBook.setId(result.getInt("id"));
            myIssueBook.setIsbn(result.getString("isbn"));
            myIssueBook.setTitle(result.getString("title"));
            myIssueBook.setStudentId(result.getString("student_id"));
            myIssueBook.setStudentName(result.getString("student_name"));
            myIssueBook.setDateBorrowed(result.getDate("date_borrowed").toLocalDate());
            myIssueBook.setDateReturned(result.getDate("date_returned").toLocalDate());
            myIssueBook.setLateFee(result.getDouble("late_fee"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return myIssueBook;
    }

    public void handleIssueBooksRightPane() {
    }

    public void handleReturnBookRightPane() {

    }

    public void searchIssueBook() {
        loading();
        int issueBookId = Integer.parseInt(returnBookSearch.getText());
        ReturnBookService.issueBookId = issueBookId;
        Task<IssueBook> getIssueBook = new Task<IssueBook>() {
            @Override
            protected IssueBook call() throws Exception {
                return getIssueBookById(issueBookId);
            }
        };
        getIssueBook.setOnSucceeded(event -> {
            IssueBook issueBook = getIssueBook.getValue();
            ReturnBookService.issueBookISBN = issueBook.getIsbn();
            returnBookIssueId.setText(String.valueOf(issueBook.getId()));
            // calculate late fee
            LocalDate currentDate = LocalDate.now();
            LocalDate returnedDate = issueBook.getDateReturned();
            long daysOverdue = ChronoUnit.DAYS.between(returnedDate, currentDate);
            if (daysOverdue > 0) {
                if (daysOverdue <= 9) {
                    returnBookOverdue.setText("0" + daysOverdue);
                } else {
                    returnBookOverdue.setText(String.valueOf(daysOverdue));
                }
                long totalFee = daysOverdue * 10;
                String str = String.valueOf(totalFee + "000");
                int len = str.length();
                StringBuilder total = new StringBuilder();
                int cnt = 0;
                for (int i = len - 1; i >= 0; i--) {
                    if (cnt == 3) {
                        total.append('.');
                        total.append(str.charAt(i));
                        cnt = 0;
                        continue;
                    }
                    total.append(str.charAt(i));
                    cnt++;
                }
                total.reverse();
                returnBookTotalFee.setText(String.valueOf(total));
            } else {
                returnBookOverdue.setText("00");
                returnBookTotalFee.setText("0đ");
            }
            returnBookIssuedDate.setText(issueBook.getDateBorrowed().toString());

            String studentId = issueBook.getStudentId();
            String isbn = issueBook.getIsbn();
            Task<Student> getStudent = new Task<Student>() {
                @Override
                protected Student call() throws Exception {
                    return getStudentByStudentId(studentId);
                }
            };
            Task<Book> getBook = new Task<Book>() {
                @Override
                protected Book call() throws Exception {
                    return getBookByISBN(isbn);
                }
            };
            getStudent.run();
            getBook.run();
            getStudent.setOnSucceeded(event1 -> {
                // set student details
                Student student = getStudent.getValue();
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    returnBookStudentId.setText(student.getStudentId());
                    returnBookStudentName.setText(student.getName());
                    returnBookStudentPhone.setText(student.getPhone());
                    returnBookStudentEmail.setText(student.getEmail());

                    //Get History
                    UserIDHistory = student.getStudentId();

                });
            });
            getStudent.setOnFailed(event1 -> {
                loaded();
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cannot get student with id = " + issueBook.getStudentId());
                    alert.showAndWait();
                });
            });
            getBook.setOnSucceeded(event2 -> {
                loaded();
                Book book = getBook.getValue();
                ReturnBookService.bookRemaining = book.getRemaining();
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    returnBookBookTitle.setText(book.getTitle());
                    returnBookBookAuthor.setText(book.getAuthor());
                    returnBookBookISBN.setText(book.getISBN());
                    returnBookBookCategory.setText(book.getCategory());
                    CategoryHistory = book.getCategory();
                    Image image;
                    if (Objects.equals(book.getSrc(), "images/null.null")) {
                        image = new Image(book.getGgimagesrc());
                    } else {
                        image = new Image(new File(book.getSrc()).toURI().toString());
                    }
                    returnBookBookCover.setImage(image);
                    // set visible
                    returnBookForm.setVisible(true);
                    returnBookRightPane.setVisible(true);

                    returnBookSearch.clear();
                });
            });
            getBook.setOnFailed(event2 -> {
                loaded();
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cannot get book with isbn = " + issueBook.getIsbn());
                    alert.showAndWait();
                });
            });
        });
        getIssueBook.setOnFailed(event -> {
            loaded();
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to issue book with id = " + issueBookId);
                alert.showAndWait();
            });
        });
        Thread getIssueBookThread = new Thread(getIssueBook);
        getIssueBookThread.setDaemon(true);
        getIssueBookThread.start();
    }

    public void handleReturnBookCenterPane() {
        returnBookCenterPane.setVisible(true);
        returnBookForm.setVisible(false);
    }

    public void updateBookAfterReturn() {
        try {
            connection = Database.connect();
            String sql = "UPDATE Book SET remaining=?, availability=? WHERE isbn=?";
            statement = connection.prepareStatement(sql);
            int remainingAfterReturn = ReturnBookService.bookRemaining + 1;
            statement.setInt(1, remainingAfterReturn);
            statement.setString(2, "Available");
            statement.setString(3, ReturnBookService.issueBookISBN);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateIssueBookAfterReturn() {
        try {
            connection = Database.connect();
            String sql = "DELETE FROM IssuedBook WHERE id =" + ReturnBookService.issueBookId;
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void returnBook() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to return this book?");
        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK) {
            loading();
            Task<Void> updateIssueBook = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateIssueBookAfterReturn();
                    return null;
                }
            };
            Task<Void> updateBook = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateBookAfterReturn();
                    return null;
                }
            };
            updateIssueBook.run();
            updateBook.run();
            updateIssueBook.setOnFailed(event -> {
                loaded();
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    Alert alert0 = new Alert(Alert.AlertType.ERROR);
                    alert0.setTitle("Error message");
                    alert0.setHeaderText(null);
                    alert0.setContentText("Cannot return book with id = " + ReturnBookService.issueBookId);
                    alert0.showAndWait();
                });
            });
            updateBook.setOnSucceeded(event -> {
                loaded();
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Information message");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Book returned successfully");
                    alert1.showAndWait();
                    // review book
                    Review(ReturnBookService.issueBookISBN);
                    returnBookForm.setVisible(false);
                    returnBookRightPane.setVisible(false);
                });
            });
            updateBook.setOnFailed(event -> {
                loaded();
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Error message");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Cannot return book with isbn = " + ReturnBookService.issueBookISBN);
                    alert2.showAndWait();
                });
            });
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Information message");
            alert1.setHeaderText(null);
            alert1.setContentText("Return book has been canceled");
            alert1.showAndWait();
        }
    }

    public void returnBookFromIssuePane(ActionEvent actionEvent) {
        IssueBook issueBook = issueBooksTableView.getSelectionModel().getSelectedItem();
        if (issueBook != null) {
            int issueBookId = issueBook.getId();
            returnBookSearch.setText(String.valueOf(issueBookId));
            issueBooksCenterPane.setVisible(false);
            issueBooksRightPane.setVisible(false);
            setSelectedBtn(returnBookBtn);
            setUnselectedBtn(issueBooksBtn);
            searchIssueBook();
            handleReturnBookCenterPane();
            handleReturnBookRightPane();
        }
    }

    public void handleSelectedIssueBook(MouseEvent event) {
        IssueBook issueBook = issueBooksTableView.getSelectionModel().getSelectedItem();
        if (issueBook != null) {
            returnBookFromIssuePaneBtn.setDisable(false);
        }
    }

    public void switchPane(ActionEvent event) {
        if (event.getSource() == homeBtn) {
            // home pane
            setSelectedBtn(homeBtn);
            handleHomePane(1, 12, false, true);
            // books pane
            booksCenterPane.setVisible(false);
            booksRightPane.setVisible(false);
            setUnselectedBtn(booksBtn);
            // students pane
            studentsCenterPane.setVisible(false);
            studentsRightPane.setVisible(false);
            setUnselectedBtn(studentsBtn);
            // issue books pane
            issueBooksCenterPane.setVisible(false);
            issueBooksRightPane.setVisible(false);
            setUnselectedBtn(issueBooksBtn);
            // return book pane
            returnBookCenterPane.setVisible(false);
            returnBookRightPane.setVisible(false);
            setUnselectedBtn(returnBookBtn);
        } else if (event.getSource() == booksBtn) {
            // home pane
            homeCenterPane.setVisible(false);
            homeRightPane.setVisible(false);
            setUnselectedBtn(homeBtn);
            // books pane
            setSelectedBtn(booksBtn);
            handleBooksCenterPane();
            handleBooksRightPane();
            // students pane
            studentsCenterPane.setVisible(false);
            studentsRightPane.setVisible(false);
            setUnselectedBtn(studentsBtn);
            // issue books pane
            issueBooksCenterPane.setVisible(false);
            issueBooksRightPane.setVisible(false);
            setUnselectedBtn(issueBooksBtn);
            // return book pane
            returnBookCenterPane.setVisible(false);
            returnBookRightPane.setVisible(false);
            setUnselectedBtn(returnBookBtn);
        } else if (event.getSource() == studentsBtn) {
            // home pane
            homeCenterPane.setVisible(false);
            homeRightPane.setVisible(false);
            setUnselectedBtn(homeBtn);
//             books pane
            booksCenterPane.setVisible(false);
            booksRightPane.setVisible(false);
            setUnselectedBtn(booksBtn);
            // students pane
            setSelectedBtn(studentsBtn);
            handleStudentsCenterPane();
            handleStudentsRightPane();
            // issue books pane
            issueBooksCenterPane.setVisible(false);
            issueBooksRightPane.setVisible(false);
            setUnselectedBtn(issueBooksBtn);
            // return book pane
            returnBookCenterPane.setVisible(false);
            returnBookRightPane.setVisible(false);
            setUnselectedBtn(returnBookBtn);
        } else if (event.getSource() == issueBooksBtn) {
            // home pane
            homeCenterPane.setVisible(false);
            homeRightPane.setVisible(false);
            setUnselectedBtn(homeBtn);
//             books pane
            booksCenterPane.setVisible(false);
            booksRightPane.setVisible(false);
            setUnselectedBtn(booksBtn);
            // students pane
            studentsCenterPane.setVisible(false);
            studentsRightPane.setVisible(false);
            setUnselectedBtn(studentsBtn);
            // issue books pane
            setSelectedBtn(issueBooksBtn);
            handleIssueBooksCenterPane();
            handleIssueBooksRightPane();

            closeStudentDetail();
            closeBookDetail();

            hiddenStudentDetailsBtn();

            hiddenBookDetailsBtn();

            hiddenIssueForm();
            // return book pane
            returnBookCenterPane.setVisible(false);
            returnBookRightPane.setVisible(false);
            setUnselectedBtn(returnBookBtn);
        } else if (event.getSource() == returnBookBtn) {
            // home pane
            homeCenterPane.setVisible(false);
            homeRightPane.setVisible(false);
            setUnselectedBtn(homeBtn);
            // books pane
            booksCenterPane.setVisible(false);
            booksRightPane.setVisible(false);
            setUnselectedBtn(booksBtn);
            // students pane
            studentsCenterPane.setVisible(false);
            studentsRightPane.setVisible(false);
            setUnselectedBtn(studentsBtn);
            // issue books pane
            issueBooksCenterPane.setVisible(false);
            issueBooksRightPane.setVisible(false);
            setUnselectedBtn(issueBooksBtn);
            // return book pane
            setSelectedBtn(returnBookBtn);
            handleReturnBookCenterPane();
            handleReturnBookRightPane();
        }
    }

    public void close() {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit");
        exitAlert.setHeaderText("Are you sure you want to exit?");
        exitAlert.setContentText("This will close the application.");
        ButtonType typeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType typeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        exitAlert.getButtonTypes().setAll(typeOK, typeCancel);
        Optional<ButtonType> result = exitAlert.showAndWait();
        if (result.get() == typeOK) {
            Platform.exit();
            System.exit(0);
        }
    }

    public void fitParent() {
        crudImageView.fitWidthProperty().bind(crudStackPane.widthProperty());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set username from login input
        username.setText(DataService.username);
        //home pane
        homeCenterPane.setVisible(false);
        homeRightPane.setVisible(false);
        setSelectedBtn(homeBtn);
        handleHomePane(1, 12, false, true);

        booksRightPane.setVisible(false);
        booksCenterPane.setVisible(false);

        rateLabel.setWrapText(true);
        rateLabel.setMaxWidth(122);
        rateLabel.setPrefHeight(Region.USE_COMPUTED_SIZE);

        studentsRightPane.setVisible(false);
        studentsCenterPane.setVisible(false);

        issueBooksCenterPane.setVisible(false);
        issueBooksRightPane.setVisible(false);


        closeStudentDetail();
        closeBookDetail();

        hiddenStudentDetailsBtn();

        hiddenBookDetailsBtn();

        hiddenIssueForm();

        returnBookCenterPane.setVisible(false);
        returnBookRightPane.setVisible(false);

        generateQRBtn.setDisable(true);

        returnBookFromIssuePaneBtn.setDisable(true);

        suggestBtn.setDisable(true);

        localCurPage = 1;
        totalPages = 0;

        calculateTotalPages();
    }
}
