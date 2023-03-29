package telas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.Controlador;
import src.Livro;
import src.Produto;
import src.Usuario;

public class Venda {

    @FXML
    private ListView<String> listview;

    @FXML
    private ListView<String> listview1;

    @FXML
    private Button btn_alugar;

    @FXML
    private TabPane tabview;

    @FXML
    private Tab tab_consultar;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_vis;

    @FXML
    private Label produto;

    @FXML
    private Label produto1;

    @FXML
    private Tab tab_cadastrar;

    @FXML
    private TextField quantidade;

    //private String codigoOriginal = "";
    private String codigoLinhaSelecionada = "";
    private static int IndexUser;

    //visualizar carrinho
    @FXML
    void click_vis(ActionEvent event) throws Exception {
        Controlador ctrl = Controlador.getInstance();
        ctrl.tela3_Carrinho();
        VisCarrinho.pegarDados(IndexUser);
        Stage stage = (Stage) btn_vis.getScene().getWindow();
        stage.close();
    }

    public static void pegarDados(int u){
        IndexUser = u; 
}

    //adicionar ao carrinho
    @FXML
    void click_add(ActionEvent event) {

    Controlador ctrl = Controlador.getInstance();
    String ctxt = codigoLinhaSelecionada; //codigo da linha selecionada
    System.out.println("Codigo ----- "+ ctxt);

    Usuario user = Controlador.getInstance().database.userList.get(IndexUser);
    
    String quantidadeStr = quantidade.getText();
    System.out.println("Quantidade ----- " + quantidadeStr);
    
   if (quantidadeStr.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Quantidade não informada!");
        return;
    }
    else if(ctxt == "")
    {
        JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!");
        return;
    }

    int qtd = Integer.parseInt(quantidadeStr);
    boolean resultado = ctrl.database.adicionarAoCarrinho(user, ctxt, qtd);
    if(!resultado) JOptionPane.showMessageDialog(null, "Operação cancelada!");
    }

    //alugar livro
    @FXML
    void click_alug(ActionEvent event) {
        Controlador ctrl = Controlador.getInstance();
        String ctxt = codigoLinhaSelecionada; //codigo da linha selecionada
        System.out.println("Codigo ----- "+ ctxt);

         Usuario user = Controlador.getInstance().database.userList.get(IndexUser);
        if(ctxt == "")
        {
        JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!");
        return;
        }
         boolean resultado = ctrl.database.promptAlugarLivro(user, ctxt);
        if(!resultado) JOptionPane.showMessageDialog(null, "Operação cancelada!");
    }

    

 // P/ visualizar os produtos na tela
   @FXML
   private void initialize() {
    Controlador ctrl = Controlador.getInstance();
    listview.getItems().clear();

    List<String> lista_nomes = new ArrayList<String>();

    for (Produto p : ctrl.database.produtoList) {
        lista_nomes.add(p.getNome() + " - " + p.getValorDeVenda());
        System.out.println("Produto: " + p.getNome());
    }

    listview.getItems().addAll(lista_nomes);


    listview.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
        if (newVal != null) {

            int index = listview.getSelectionModel().getSelectedIndex();
  
            codigoLinhaSelecionada = ctrl.database.produtoList.get(index).getCodigo();
            System.out.println("Codigo da linha selecionada: " + codigoLinhaSelecionada);
        }
    });
    listview1.getItems().clear();

    List<String> lista_nomes2 = new ArrayList<String>();

    for (Produto p : ctrl.database.produtoList) {
        if(p.getClass() == Livro.class){
        lista_nomes2.add(p.getNome() + " - " + p.getValorDeVenda());
        System.out.println("Produto: " + p.getNome());
        }

    }

    listview1.getItems().addAll(lista_nomes2);

    listview1.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
        if (newVal != null) {
       
            int index = listview1.getSelectionModel().getSelectedIndex();
            
            codigoLinhaSelecionada = ctrl.database.produtoList.get(index).getCodigo();
            System.out.println("Codigo da linha selecionada: " + codigoLinhaSelecionada);
        }
    });
}
}