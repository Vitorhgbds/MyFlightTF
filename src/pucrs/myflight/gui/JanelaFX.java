package pucrs.myflight.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pucrs.myflight.modelo.*;

public class JanelaFX extends Application {

	final SwingNode mapkit = new SwingNode();

	private GerenciadorCias gerCias;
	private GerenciadorAeroportos gerAero;
	private GerenciadorRotas gerRotas;
	private GerenciadorAeronaves gerAvioes;
	private GerenciadorPaises gerPaises;
	private GerenciadorMapa gerenciador;

	private EventosMouse mouse;

	@Override
	public void start(Stage primaryStage) throws Exception {

		setup();

		GeoPosition poa = new GeoPosition(-30.05, -51.18);
		gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
		mouse = new EventosMouse();
		gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);

		createSwingContent(mapkit);

		BorderPane pane = new BorderPane();
		GridPane leftPane = new GridPane();

		leftPane.setAlignment(Pos.CENTER);
		leftPane.setHgap(30);
		leftPane.setVgap(30);
		leftPane.setPadding(new Insets(10, 10, 10, 10));

		Button btnConsulta1 = new Button("Consulta 1");
		Button btnConsulta2 = new Button("Consulta 2");
		Button btnConsulta3 = new Button("Consulta 3");
		Button btnConsulta4 = new Button("Consulta 4");

//		ObservableList<CiaAerea> ciaList = FXCollections.observableList(gerCias.listarTodas());
//		ComboBox<CiaAerea> ciasCombo = new ComboBox<>(ciaList);

		leftPane.add(btnConsulta1, 0, 0);
		leftPane.add(btnConsulta2, 2, 0);
		leftPane.add(btnConsulta3, 3, 0);
		leftPane.add(btnConsulta4, 4, 0);
		//leftPane.add(ciasCombo, 5, 0);



		btnConsulta1.setOnAction(e -> {
			consulta1();
		});
		btnConsulta2.setOnAction(e -> {
			consulta2();
		});


		pane.setCenter(mapkit);
		pane.setTop(leftPane);

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Mapas com JavaFX");
		primaryStage.show();

	}

	// Inicializando os dados aqui...
	private void setup() {

		gerCias = GerenciadorCias.getInstance();
		gerCias.carregaDados("airlines.dat");
		gerPaises = GerenciadorPaises.getInstance();
		gerPaises.carregaDados("countries.dat");
		gerAvioes = GerenciadorAeronaves.getInstance();
		gerAvioes.carregaDados("equipment.dat");
		gerAero = GerenciadorAeroportos.getInstance();
		gerAero.carregaDados("airports.dat");
		gerRotas = GerenciadorRotas.getInstance();
		gerRotas.carregaDados("routes.dat");

	}

	private void consulta1() {
		// Lista para armazenar o resultado da consulta
		gerenciador.clear();

		ObservableList<CiaAerea> ciaList = FXCollections.observableList(gerCias.listarTodas());
		ComboBox<CiaAerea> ciaCombo = new ComboBox<>(ciaList);

		FlowPane pane2 = new FlowPane();
		Label label = new Label("Selecione uma companhia aerea para mostrar rotas!");
		Button btn = new Button("Confirmar");
		pane2.setHgap(20);
		pane2.setVgap(30);
		pane2.setAlignment(Pos.CENTER);
		pane2.getChildren().addAll(label,ciaCombo,btn);
		Scene sc = new Scene(pane2);
		Stage st = new Stage();
		st.setScene(sc);
		st.setTitle("Seleção de companhia");
		st.showAndWait();
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				st.close();
			}
		});

		String ciaCodigo = ciaCombo.getValue().getCodigo();
		gerRotas.buscaPorCia(ciaCodigo).forEach(rota -> {
			Tracado tr = new Tracado();
			tr.setWidth(5);
			tr.setCor(new Color(0,0,0,60));
			tr.addPonto(rota.getOrigem().getLocal());
			tr.addPonto(rota.getDestino().getLocal());
			gerenciador.addTracado(tr);
		});



		// Adiciona os locais de cada aeroporto (sem repetir) na lista de
		// waypoints

//		lstPoints.add(new MyWaypoint(Color.RED, poa.getCodigo(), poa.getLocal(), 5));
//		lstPoints.add(new MyWaypoint(Color.RED, gru.getCodigo(), gru.getLocal(), 5));
//		lstPoints.add(new MyWaypoint(Color.RED, lis.getCodigo(), lis.getLocal(), 5));
//		lstPoints.add(new MyWaypoint(Color.RED, mia.getCodigo(), mia.getLocal(), 5));

		// Para obter um ponto clicado no mapa, usar como segue:
		// GeoPosition pos = gerenciador.getPosicao();

		// Informa o resultado para o gerenciador
		//gerenciador.setPontos(lstPoints);

		// Quando for o caso de limpar os traçados...
		// gerenciador.clear();

		gerenciador.getMapKit().repaint();
	}

	public void consulta2(){
		List<MyWaypoint> lstPoints = new ArrayList<>();

		gerenciador.clear();

		ObservableList<Pais> paisList = FXCollections.observableList(gerPaises.listarTodos());
		ComboBox<Pais> paisCombo = new ComboBox<>(paisList);

		FlowPane pane2 = new FlowPane();
		Label label = new Label("Selecione o país para visualizar o fluxo no!");
		Button btn = new Button("Confirmar");
		pane2.setHgap(20);
		pane2.setVgap(30);
		pane2.setAlignment(Pos.CENTER);
		pane2.getChildren().addAll(label,paisCombo,btn);
		Scene sc = new Scene(pane2);
		Stage st = new Stage();
		st.setScene(sc);
		st.setTitle("Seleção de Paises");
		st.showAndWait();
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				st.close();
			}
		});

//		.forEach((aeroporto, numero) -> {
//			if(numero < 10) {
//				lstPoints.add(new MyWaypoint(new Color(0,0,255,50), aeroporto.getCodigo(),
//						aeroporto.getLocal(), 10));
//			}
//			else if(numero < 50) {
//				lstPoints.add(new MyWaypoint(new Color(255,255,0,50), aeroporto.getCodigo(),
//						aeroporto.getLocal(), 15));
//			}
//			else {
//				lstPoints.add(new MyWaypoint(new Color(255,0,0,50), aeroporto.getCodigo(),
//						aeroporto.getLocal(), 20));
//			}
//		});

		Map<Aeroporto, Long> contador = gerRotas.buscaPorCia("AD").stream()
				.collect(Collectors.groupingBy(Rota::getOrigem,Collectors.counting()));



		gerenciador.setPontos(lstPoints);
		gerenciador.getMapKit().repaint();
	}

	private class EventosMouse extends MouseAdapter {
		private int lastButton = -1;

		@Override
		public void mousePressed(MouseEvent e) {
			JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
			GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
			// System.out.println(loc.getLatitude()+", "+loc.getLongitude());
			lastButton = e.getButton();
			// Botão 3: seleciona localização
			if (lastButton == MouseEvent.BUTTON3) {
				gerenciador.setPosicao(loc);
				gerenciador.getMapKit().repaint();
			}
		}
	}

	private void createSwingContent(final SwingNode swingNode) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				swingNode.setContent(gerenciador.getMapKit());
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
