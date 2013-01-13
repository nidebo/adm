package com.example.boox;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import usuarios.Persona;
import usuarios.Usuario;
import librosGoogle.VolumeInfo;
import librosGoogle.VolumeInfo.ImageLinks;
import listasLibros.*;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyBD extends SQLiteOpenHelper {
	
	Context context;
	public MyBD(Context context) {
		super(context, "bdlibros.db", null, 1);
		this.context=context;
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
/*	arg0.execSQL("CREATE TABLE todos (id VARCHAR(20),isbn  VARCHAR(20), title VARCHAR(20), subtitle VARCHAR(50),author VARCHAR(500), photo VARCHAR(500), editorial VARCHAR(50), description VARCHAR(1000), language VARCHAR(20), averageRating FLOAT);");
	arg0.execSQL("CREATE TABLE amigos (id  VARCHAR(20), name VARCHAR(20),  cp INTEGER )");
	*/
	}
		
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	
	
/****  MANEJO LISTA todos  *****/
	
	/*Inserta libro en la bilioteca */
	
	public boolean InsertarLibro(Libro book){
		SQLiteDatabase bd = getWritableDatabase();
		String id = book.getId();
		if(id == null) return false;
		String isbn = book.getIsbn();
		if(isbn == null) isbn=" ";
		String title = book.getTitulo();
		if(title == null) title = " ";
		String subtitle = book.getTitulo();
		if(subtitle == null) subtitle = " ";
		String authors;
		if(book.getAutores() == null) authors = " ";
		else{
			authors = book.getAutores().get(0);
			for(int i=0;i<book.getAutores().size();i++) authors.concat(','+ book.getAutores().get(i));
		}
		String photo;
		if(book.getImageLinks() == null) photo = " ";
		else{
			if (book.getImageLinks().getMedium() == null) photo = " ";
			else photo = book.getImageLinks().getMedium();
		}
		String publisher=book.getEditorial();
		if(publisher == null) publisher = " ";
		String description = book.getDescripcion();
		if(description == null) description = " ";
		String language = book.getIdioma();
		if(language == null) language = " ";
		Float average = book.getPuntuacionMedia();
		if(average == null) average = (float) 0.0;
		String insert = "INSERT INTO todos (id,isbn, title, subtitle, author, photo, editorial, description, language,averageRating) VALUES('"+
						id+"','"+isbn +"','"+title+"','"+subtitle+"','"+authors+"','"+photo +"','"+publisher+"','"+description+"','"+language+"','"+Float.toString(average)+"');";
		try{
			bd.execSQL(insert);
		}catch(SQLException e1){
			Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
			return false;			
		}
		return true;
	}
	
	/*Lista todos los libros de la biblioteca*/
	
	public ListaLibros ListaDeTodosLibros(){
		SQLiteDatabase bd = getReadableDatabase();
		Libro libro= new Libro();
		ListaLibros lista_libros = new ListaLibros("todos");
		Cursor consulta = null;
		try{
			consulta = bd.rawQuery("SELECT * FROM todos", null);
		}
		catch(SQLiteException e1){
			Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
		}
		if(consulta.moveToFirst()){
			do{
				libro= new Libro(consulta.getString(0));
				libro.setIsbn(consulta.getString(1));
				libro.setTitulo(consulta.getString(2));
				libro.setSubtitulo(consulta.getString(3));
				String[] aux = consulta.getString(4).split(",");
				ArrayList<String> authors = new ArrayList<String>(); 
				for(int i=0;i<aux.length;i++)	authors.add(aux[i]);
				libro.setAutores(authors);
				VolumeInfo volume = new VolumeInfo();
				VolumeInfo.ImageLinks imagen = volume.new ImageLinks();
				imagen.setMedium(consulta.getString(5));
				libro.setImageLinks(imagen);
				libro.setEditorial(consulta.getString(6));
				libro.setDescripcion(consulta.getString(7));
				libro.setIdioma(consulta.getString(8));
				libro.setPuntuacionMedia(consulta.getFloat(9));	
				lista_libros.addLibro(libro);
			}while(consulta.moveToNext());
		}
		return lista_libros;
	}
	
	/*Devuelve el libro que se busca pasando como parámetro de búsqueda el id*/
	
	public Libro DetalleLibroId(String id){
		SQLiteDatabase bd = getReadableDatabase();
		Libro libro= null;
		Cursor consulta = null;
		try{
			consulta = bd.rawQuery("SELECT * FROM todos WHERE id= ? ", new String[]{id});
		}catch(SQLException e1){
			Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
		}
		if(consulta.moveToFirst()){
			libro= new Libro(consulta.getString(0));
			libro.setIsbn(consulta.getString(1));
			libro.setTitulo(consulta.getString(2));
			libro.setSubtitulo(consulta.getString(3));
			String[] aux = consulta.getString(4).split(",");
			ArrayList<String> authors = new ArrayList<String>(); 
			for(int i=0;i<aux.length;i++)	authors.add(aux[i]);
			libro.setAutores(authors);
			VolumeInfo volume = new VolumeInfo();
			VolumeInfo.ImageLinks imagen = volume.new ImageLinks();
			imagen.setMedium(consulta.getString(5));
			libro.setImageLinks(imagen);
			libro.setEditorial(consulta.getString(6));
			libro.setDescripcion(consulta.getString(7));
			libro.setIdioma(consulta.getString(8));
			libro.setPuntuacionMedia(consulta.getFloat(9));	
		}
		return libro;
	}
	
	/*borra todos los libros de la biblioteca*/
	public void BorrarLibros(){
		SQLiteDatabase bd = getWritableDatabase();
		String delete = "DELETE FROM todos";
		try{
			bd.execSQL(delete);
		}catch(SQLException e1){
			Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	/*borrar un libro concreto de la biblioteca*/
	public void BorrarLibro(String id){
		SQLiteDatabase bd = getWritableDatabase();
		String delete = "DELETE FROM todos WHERE id = " + id;
		try{
			bd.execSQL(delete);
		}catch(SQLException e1){
			Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	/********   MANEJO AMIGO ******/
	
	/*Inserta un amigo*/
	public void InsertarAmigo(Persona user){
		SQLiteDatabase bd = getWritableDatabase();
		String insert = "INSERT INTO amigos (id, name, surname, cp) VALUES('"+
						user.getId()+"','"+user.getNombre()+"','"+user.getCodigoPostal()+"');";
		try{
			bd.execSQL(insert);
		}catch(SQLException e1){
			Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	/*Devuelve los detalles del amigo*/
	public Usuario DetallesUsuarioId(int id){
		SQLiteDatabase bd = getReadableDatabase();
		Usuario pers= (Usuario) new Persona(null);
		Cursor consulta = null;
		try{
		 consulta = bd.rawQuery("SELECT * FROM amigos WHERE ID= ? ", new String[]{Integer.toString(id)});
		}catch(SQLException e1){
			Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
		}
		if(consulta.moveToFirst()){
				pers.setIdUsuario(consulta.getString(0));
				pers.setNombre(consulta.getString(1));		
				pers.setCodigoPostal(consulta.getString(3));
		}
		return pers;
	}
	
	/*Borra todos los amigos de la lista*/
	public void BorrarAmigos(){
		SQLiteDatabase bd = getWritableDatabase();
		String delete1 = "DELETE FROM amigos";
		try{
			bd.execSQL(delete1);
		}catch(SQLException e1){
			Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	/*Borra un amigo en concreto*/
	public void BorrarAmigo(String id){
		SQLiteDatabase bd = getWritableDatabase();
		String delete1 = "DELETE FROM amigo WHERE id="+id;
		try{
			bd.execSQL(delete1);
		}catch(SQLException e1){
			Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	
	/*******    MANEJO LISTAS *********/
	
	/*Creacion de una nueva lista*/
	public void CrearNuevaLista(String nombreLista){
		SQLiteDatabase bd = getWritableDatabase();
		String insert = "CREATE TABLE " + nombreLista + " (id  VARCHAR(20))";
		try{
			bd.execSQL(insert);
		}catch(SQLException e1){
			Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
		
	/*Insertamos un libro en la lista*/
	public void InsertarLibroEnLista(String lista, Libro libro){
		SQLiteDatabase bd = getWritableDatabase();
		String insert = "INSERT INTO " + lista + "(id) VALUES('"+libro.getId()+"');";
		InsertarLibro(libro);
		try{
			bd.execSQL(insert);
		}catch(SQLException e1){
			Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	/*Devuelve un objeto ListaLibros con todos los libros de la lista que se pasa como parametro*/
	
	public ListaLibros ListaDeLibros(String lista){
		SQLiteDatabase bd = getReadableDatabase();
		Libro libro= new Libro();
		ListaLibros lista_libros = new ListaLibros(lista);
		Cursor consulta = null;
		try{
			consulta = bd.rawQuery("SELECT * FROM "+lista, null);
		}
		catch(SQLiteException e1){
			Toast.makeText(context, "No existe la lista", Toast.LENGTH_LONG).show();
		}
		if(consulta.moveToFirst()){
			do{
				libro= new Libro(consulta.getString(0));
				libro.setIsbn(consulta.getString(1));
				libro.setTitulo(consulta.getString(2));
				libro.setSubtitulo(consulta.getString(3));
				String[] aux = consulta.getString(4).split(",");
				ArrayList<String> authors = new ArrayList<String>(); 
				for(int i=0;i<aux.length;i++)	authors.add(aux[i]);
				libro.setAutores(authors);
				VolumeInfo volume = new VolumeInfo();
				VolumeInfo.ImageLinks imagen = volume.new ImageLinks();
				imagen.setMedium(consulta.getString(5));
				libro.setImageLinks(imagen);
				libro.setEditorial(consulta.getString(6));
				libro.setDescripcion(consulta.getString(7));
				libro.setIdioma(consulta.getString(8));
				libro.setPuntuacionMedia(consulta.getFloat(9));	
				lista_libros.addLibro(libro);
			}while(consulta.moveToNext());
		}
		return lista_libros;
	}
	
	
	/*Elimina todos los libros de "lista"*/
	
	public void VaciarLista(String lista){
		SQLiteDatabase bd = getWritableDatabase();
		String delete1 = "DELETE FROM " + lista;
		try{
			bd.execSQL(delete1);
			}
		catch(SQLException e1){
			Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	/*Elimina una lista*/
	
	public void EliminarLista(String lista){
		SQLiteDatabase bd = getWritableDatabase();
		String delete1 = "DROP TABLE "+ lista;
		try{
			bd.execSQL(delete1);
			}
		catch(SQLException e1){
			Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	/*
	public ArrayList ListadoListas(){
		SQLiteDatabase bd = getReadableDatabase();
		ArrayList<String> lista = new ArrayList();
		Cursor consulta = null;
		try{
			consulta = bd.rawQuery("SELECT * FROM listas", null);
		}
		catch(SQLiteException e1){
			Toast.makeText(context, "No existe la lista", Toast.LENGTH_LONG).show();
		}
		if(consulta.moveToFirst()){
			do{
				lista.add(consulta.getString(1));
			}while(consulta.moveToNext());
		}
		return lista;
	}*/
	
	
/****  MANEJO TEMPORAL  *****/
	
	/*Inserta libro en temporal */
	
	public boolean InsertarTemporal(Libro book){
		SQLiteDatabase bd = getWritableDatabase();
		String id = book.getId();
		if(id == null) return false;
		String isbn = book.getIsbn();
		if(isbn == null) isbn=" ";
		String title = book.getTitulo();
		if(title == null) title = " ";
		String subtitle = book.getTitulo();
		if(subtitle == null) subtitle = " ";
		String authors;
		if(book.getAutores() == null) authors = " ";
		else{
			authors = book.getAutores().get(0);
			for(int i=0;i<book.getAutores().size();i++) authors.concat(','+ book.getAutores().get(i));
		}
		String photo;
		if(book.getImageLinks() == null) photo = " ";
		else{
			if (book.getImageLinks().getMedium() == null) photo = " ";
			else photo = book.getImageLinks().getMedium();
		}
		String publisher=book.getEditorial();
		if(publisher == null) publisher = " ";
		String description = book.getDescripcion();
		if(description == null) description = " ";
		String language = book.getIdioma();
		if(language == null) language = " ";
		Float average = book.getPuntuacionMedia();
		if(average == null) average = (float) 0.0;
		String insert = "INSERT INTO temporal (id,isbn, title, subtitle, author, photo, editorial, description, language,averageRating) VALUES('"+
						id+"','"+isbn +"','"+title+"','"+subtitle+"','"+authors+"','"+photo +"','"+publisher+"','"+description+"','"+language+"','"+Float.toString(average)+"');";
		try{
			bd.execSQL(insert);
		}catch(SQLException e1){
			e1.printStackTrace();
			return false;			
		}
		return true;
	}
	
	
	/*Devuelve el libro temporal (Asumimos que siempre habrá un libro)*/
	
	public Libro DetalleTemporal(){
		SQLiteDatabase bd = getReadableDatabase();
		Libro libro= null;
		Cursor consulta = null;
		try{
			consulta = bd.rawQuery("SELECT * FROM temporal", null);
		}catch(SQLException e1){
			e1.printStackTrace();
		}
		if(consulta.moveToFirst()){
			libro= new Libro(consulta.getString(0));
			libro.setIsbn(consulta.getString(1));
			libro.setTitulo(consulta.getString(2));
			libro.setSubtitulo(consulta.getString(3));
			String[] aux = consulta.getString(4).split(",");
			ArrayList<String> authors = new ArrayList<String>(); 
			for(int i=0;i<aux.length;i++)	authors.add(aux[i]);
			libro.setAutores(authors);
			VolumeInfo volume = new VolumeInfo();
			VolumeInfo.ImageLinks imagen = volume.new ImageLinks();
			imagen.setMedium(consulta.getString(5));
			libro.setImageLinks(imagen);
			libro.setEditorial(consulta.getString(6));
			libro.setDescripcion(consulta.getString(7));
			libro.setIdioma(consulta.getString(8));
			libro.setPuntuacionMedia(consulta.getFloat(9));	
		}
		return libro;
	}
	
	/*borra el libro temporal*/
	public void BorrarTemporal(){
		SQLiteDatabase bd = getWritableDatabase();
		String delete = "DELETE FROM temporal";
		try{
			bd.execSQL(delete);
		}catch(SQLException e1){
			e1.printStackTrace();
		}
	}
	
}

