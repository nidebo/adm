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
		super(context, "myfile.db", null, 1);
		this.context=context;
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
	arg0.execSQL("CREATE TABLE todos (id VARCHAR(20),isbn  VARCHAR(20), title VARCHAR(20), subtitle VARCHAR(50),author VARCHAR(500), photo VARCHAR(500), editorial VARCHAR(50), description VARCHAR(1000), language VARCHAR(20), averageRating FLOAT);");
	arg0.execSQL("CREATE TABLE amigos (id  VARCHAR(20), name VARCHAR(20), surname VARCHAR(50),  cp INTEGER )");
	arg0.execSQL("CREATE TABLE listas (id INTEGER PRIMARY KEY AUTOINCREMENT,nombre VARCHAR(20))");
	Cursor consulta = null;
	try{
		consulta = arg0.rawQuery("SELECT * FROM listas", null);
	}catch(SQLException e1){
		e1.printStackTrace();
	}
	if(consulta.moveToFirst()){
		do{
			String tabla=consulta.getString(0);
			if(tabla!=null) arg0.execSQL("CREATE TABLE "+tabla+ " (id  VARCHAR(20))");
		}while(consulta.moveToNext());
	}
	}
		
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	
	public void onDelete(){
		SQLiteDatabase bd = getWritableDatabase();
		String delete1 = "DELETE FROM *; DROP TABLE todos; DROP TABLE amigos;";
		bd.execSQL(delete1);
		Cursor consulta = null;
		try{
			consulta = bd.rawQuery("SELECT * FROM listas", null);
		}catch(SQLException e1){
			e1.printStackTrace();
		}
		if(consulta.moveToFirst()){
			do{
				String tabla=consulta.getString(0);
				if(tabla!=null) bd.execSQL(" DROP TABLE"+tabla);
			}while(consulta.moveToNext());
		}
		try{
			bd.execSQL(delete1+"DROP TABLE listas");
			}
		catch(SQLException e1){
			e1.printStackTrace();
		}
	}
	
	
/****  MANEJO LISTA todos  *****/
	
	public boolean InsertarLibro(Libro book){
		SQLiteDatabase bd = getWritableDatabase();
		String id = book.getIsbn();
		if(id == null) return false;
		String isbn = book.getIsbn();
		if(isbn == null) isbn="Desconocido";
		String title = book.getTitulo();
		if(title == null) title = "Desconocido";
		String subtitle = book.getTitulo();
		if(subtitle == null) subtitle = "----";
		String authors;
		if(book.getAutores() == null) authors = "Desconocido";
		else{
			authors = book.getAutores().get(0);
			for(int i=0;i<book.getAutores().size();i++) authors.concat(','+ book.getAutores().get(i));
		}
		String photo;
		if(book.getImageLinks() == null) photo = "Desconocido";
		else{
			if (book.getImageLinks().getMedium() == null) photo = "Desconocido";
			else photo = book.getImageLinks().getMedium();
		}
		String publisher=book.getEditorial();
		if(publisher == null) publisher = "Desconocido";
		String description = book.getDescripcion();
		if(description == null) description = "Desconocido";
		String language = book.getIdioma();
		if(language == null) language = "Desconocido";
		Float average = book.getPuntuacionMedia();
		if(average == null) average = (float) 0.0;
		String insert = "INSERT INTO todos (id,isbn, title, subtitle, author, photo, editorial, description, language,averageRating) VALUES('"+
						id+"','"+isbn +"','"+title+"','"+subtitle+"','"+authors+"','"+photo +"','"+publisher+"','"+description+"','"+language+"','"+Float.toString(average)+"');";
		try{
			bd.execSQL(insert);
		}catch(SQLException e1){
			e1.printStackTrace();
			return false;			
		}
		return true;
	}
	
	public ListaLibros ListaDeTodosLibros(){
		SQLiteDatabase bd = getReadableDatabase();
		Libro libro= new Libro();
		ListaLibros lista_libros = new ListaLibros("todos");
		Cursor consulta = null;
		try{
			consulta = bd.rawQuery("SELECT * FROM todos", null);
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
	
	public Libro DetalleLibroId(String id){
		SQLiteDatabase bd = getReadableDatabase();
		Libro libro= null;
		Cursor consulta = null;
		try{
			consulta = bd.rawQuery("SELECT * FROM todos WHERE isbn= ? ", new String[]{id});
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
	
	public void BorrarLibros(){
		SQLiteDatabase bd = getWritableDatabase();
		String delete = "DELETE FROM todos";
		try{
			bd.execSQL(delete);
		}catch(SQLException e1){
			e1.printStackTrace();
		}
	}
	
	public void BorrarLibro(String id){
		SQLiteDatabase bd = getWritableDatabase();
		String delete = "DELETE FROM todos WHERE id = " + id;
		try{
			bd.execSQL(delete);
		}catch(SQLException e1){
			e1.printStackTrace();
		}
	}
	
	/********   MANEJO AMIGO ******/
	
	public void InsertarAmigo(Persona user){
		SQLiteDatabase bd = getWritableDatabase();
		String insert = "INSERT INTO amigos (id, name, cp) VALUES('"+
						user.getId()+"','"+user.getNombre()+"','"+user.getCodigoPostal()+"');";
		try{
			bd.execSQL(insert);
		}catch(SQLException e1){
			e1.printStackTrace();
		}
	}

	
	public Usuario DetallesUsuarioId(int id){
		SQLiteDatabase bd = getReadableDatabase();
		Usuario pers= (Usuario) new Persona(null);
		Cursor consulta = null;
		try{
		 consulta = bd.rawQuery("SELECT * FROM amigos WHERE ID= ? ", new String[]{Integer.toString(id)});
		}catch(SQLException e1){
			e1.printStackTrace();
		}
		if(consulta.moveToFirst()){
				pers.setIdUsuario(consulta.getString(0));
				pers.setNombre(consulta.getString(1));		
				pers.setCodigoPostal(consulta.getString(3));
		}
		return pers;
	}
	
	public void BorrarAmigos(){
		SQLiteDatabase bd = getWritableDatabase();
		String delete1 = "DELETE FROM amigos";
		try{
			bd.execSQL(delete1);
		}catch(SQLException e1){
			e1.printStackTrace();
		}
	}
	
	public void BorrarAmigo(String id){
		SQLiteDatabase bd = getWritableDatabase();
		String delete1 = "DELETE FROM amigo WHERE id="+id;
		try{
			bd.execSQL(delete1);
		}catch(SQLException e1){
			e1.printStackTrace();
		}
	}
	
	
	/*******    MANEJO LISTAS *********/
	
	public void CrearNuevaLista(String nombreLista){
		SQLiteDatabase bd = getWritableDatabase();
		String insert = "CREATE TABLE " + nombreLista + " (id  VARCHAR(20))";
		try{
			bd.execSQL(insert);
		}catch(SQLException e1){
			e1.printStackTrace();
		}
		insert = "INSERT INTO listas (id,nombre) VALUES(null,'"+nombreLista+"');";
		try{
			bd.execSQL(insert);
		}catch(SQLException e1){
			e1.printStackTrace();
		}
	}
		
	
	public void InsertarLibroEnLista(String lista, Libro libro){
		SQLiteDatabase bd = getWritableDatabase();
		String insert = "INSERT INTO " + lista + "(id) VALUES('"+libro.getId()+"');";
		InsertarLibro(libro);
		try{
			bd.execSQL(insert);
		}catch(SQLException e1){
			e1.printStackTrace();
		}
	}
	
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
	
	public void VaciarLista(String lista){
		SQLiteDatabase bd = getWritableDatabase();
		String delete1 = "DELETE FROM " + lista;
		try{
			bd.execSQL(delete1);
			}
		catch(SQLException e1){
			e1.printStackTrace();
		}
	}
	
	public void EliminarLista(String lista){
		SQLiteDatabase bd = getWritableDatabase();
		String delete1 = "DELETE FROM listas WHERE nombre="+lista+"; DROP TABLE "+ lista;
		try{
			bd.execSQL(delete1);
			}
		catch(SQLException e1){
			e1.printStackTrace();
		}
	}
	
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
	}
}

