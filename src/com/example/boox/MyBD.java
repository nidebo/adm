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
	
	public MyBD(Context context,String nombre) {
		super(context, nombre+".db", null, 1);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
	arg0.execSQL("CREATE TABLE todos (id VARCHAR(20),isbn  VARCHAR(20), title VARCHAR(20), subtitle VARCHAR(50),author VARCHAR(500), photo VARCHAR(500), editorial VARCHAR(50), description VARCHAR(1000), language VARCHAR(20), averageRating FLOAT);");
	arg0.execSQL("CREATE TABLE amigos (id  VARCHAR(20), name VARCHAR(20),  cp INTEGER )");
	arg0.execSQL("CREATE TABLE temporal (id VARCHAR(20),isbn  VARCHAR(20), title VARCHAR(20), subtitle VARCHAR(50),author VARCHAR(500), photo VARCHAR(500), editorial VARCHAR(50), description VARCHAR(1000), language VARCHAR(20), averageRating FLOAT);");

	}
		
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	
	public void crearTodos(){
		SQLiteDatabase bd = getWritableDatabase();
		bd.execSQL("CREATE TABLE todos (id VARCHAR(20),isbn  VARCHAR(20), title VARCHAR(20), subtitle VARCHAR(50),author VARCHAR(500), photo VARCHAR(500), editorial VARCHAR(50), description VARCHAR(1000), language VARCHAR(20), averageRating FLOAT);");
	}
/****  MANEJO LISTA todos  *****/
	
	/*Inserta libro en la bilioteca */
	
	public void InsertarLibro(Libro book) throws SQLException{
		SQLiteDatabase bd = getWritableDatabase();
		String id = book.getId();
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
		String insert = "INSERT INTO todos (id,isbn, title, subtitle, author, photo, editorial, description, language,averageRating) VALUES(\""+
						id+"\",\""+isbn +"\",\""+title+"\",\""+subtitle+"\",\""+authors+"\",\""+photo +"\",\""+publisher+"\",\""+description+"\",\""+language+"\",\""+Float.toString(average)+"\");";
		bd.execSQL(insert);
	}
	
	/*Lista todos los libros de la biblioteca*/
	
	public ListaLibros ListaDeTodosLibros()throws SQLException{
		SQLiteDatabase bd = getReadableDatabase();
		Libro libro= new Libro();
		ListaLibros lista_libros = new ListaLibros("todos");
		Cursor consulta = null;
			consulta = bd.rawQuery("SELECT * FROM todos", null);
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
	
	/*Devuelve el libro que se busca pasando como par�metro de b�squeda el id*/
	
	public Libro DetalleLibroId(String id)throws SQLException{
		SQLiteDatabase bd = getReadableDatabase();
		Libro libro= null;
		Cursor consulta = null;
		consulta = bd.rawQuery("SELECT * FROM todos WHERE id= ? ", new String[]{id});
		
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
	public void BorrarLibros()throws SQLException{
		SQLiteDatabase bd = getWritableDatabase();
		String delete = "DELETE FROM todos";
		
			bd.execSQL(delete);
		
	}
	
	/*borrar un libro concreto de la biblioteca*/
	public void BorrarLibro(String id)throws SQLException{
		SQLiteDatabase bd = getWritableDatabase();
		String delete = "DELETE FROM todos WHERE id = '" + id+"'";
		bd.execSQL(delete);
		
	}
	
	/********   MANEJO AMIGO ******/
	
	/*Inserta un amigo*/
	public void InsertarAmigo(Persona user)throws SQLException{
		SQLiteDatabase bd = getWritableDatabase();
		String insert = "INSERT INTO amigos (id, name, surname, cp) VALUES('"+
						user.getId()+"','"+user.getNombre()+"','"+user.getCP()+"');";
		bd.execSQL(insert);
		
	}

	/*Devuelve los detalles del amigo*/
	public Usuario DetallesUsuarioId(int id)throws SQLException{
		SQLiteDatabase bd = getReadableDatabase();
		Usuario pers= (Usuario) new Persona(null);
		Cursor consulta = null;
		consulta = bd.rawQuery("SELECT * FROM amigos WHERE ID= ? ", new String[]{Integer.toString(id)});
		
		if(consulta.moveToFirst()){
				pers.setIdUsuario(consulta.getString(0));
				pers.setNombre(consulta.getString(1));		
				pers.setCodigoPostal(consulta.getString(3));
		}
		return pers;
	}
	
	/*Borra todos los amigos de la lista*/
	public void BorrarAmigos()throws SQLException{
		SQLiteDatabase bd = getWritableDatabase();
		String delete1 = "DELETE FROM amigos";
		bd.execSQL(delete1);
		
	}
	
	/*Borra un amigo en concreto*/
	public void BorrarAmigo(String id)throws SQLException{
		SQLiteDatabase bd = getWritableDatabase();
		String delete1 = "DELETE FROM amigo WHERE id='"+id+"'";
		bd.execSQL(delete1);
		
	}
	
	
	/*******    MANEJO LISTAS *********/
	
	/*Creacion de una nueva lista*/
	public boolean CrearNuevaLista(String nombreLista) throws SQLException{
		SQLiteDatabase bd = getWritableDatabase();
		Boolean noexiste=true;
		nombreLista = nombreLista.replaceAll(" ", "000");
		String insert = "CREATE TABLE " + nombreLista + " (id  VARCHAR(20))";
		ArrayList<String> liston = ListadoNombreListas();
		if(liston.contains(nombreLista)|| nombreLista.equals("android_metadata") || nombreLista.equals("amigos") || nombreLista.equals("temporal") || nombreLista.equals("publicos"))
			noexiste = false;

		if(noexiste)	bd.execSQL(insert);
		return noexiste;
	}
		
	/*Insertamos un libro en la lista*/
	public void InsertarLibroEnLista(String lista, Libro libro) throws SQLException{
		SQLiteDatabase bd = getWritableDatabase();
		lista = lista.replaceAll(" ", "000");
		String insert = "INSERT INTO " + lista + "(id) VALUES('"+libro.getId()+"');";
		bd.execSQL(insert);
		
	}
	
	/*Devuelve un objeto ListaLibros con todos los libros de la lista que se pasa como parametro*/
	
	public ListaLibros ListaDeLibros(String lista) throws SQLException{
		SQLiteDatabase bd = getReadableDatabase();
		Libro libro= new Libro();
		ListaLibros lista_libros = new ListaLibros(lista);
		Cursor consulta = null;
		lista = lista.replaceAll(" ", "000");
		consulta = bd.rawQuery("SELECT * FROM "+lista, null);
		
		if(consulta.moveToFirst()){
			do{
				String id = consulta.getString(0);
				libro = DetalleLibroId(id);
				lista_libros.addLibro(libro);
			}while(consulta.moveToNext());
		}
		return lista_libros;
	}
	
	
	/*Elimina todos los libros de "lista"*/
	
	public void VaciarLista(String lista) throws SQLException{
		SQLiteDatabase bd = getWritableDatabase();
		lista = lista.replaceAll(" ", "000");
		String delete1 = "DELETE FROM " + lista;
		bd.execSQL(delete1);
		
	}
	
	/*Elimina una lista*/
	
	public void EliminarLista(String lista) throws SQLException{
		SQLiteDatabase bd = getWritableDatabase();
		lista = lista.replaceAll(" ", "000");
		String delete1 = "DROP TABLE "+ lista;
		bd.execSQL(delete1);
		
	}
	
	
	public ArrayList<String> ListadoNombreListas(){
		SQLiteDatabase bd = getReadableDatabase();
		ArrayList<String> todolistas = new ArrayList();
		Cursor consulta = null;
			consulta = bd.rawQuery("select name from sqlite_master where type = 'table';", null);
		if(consulta.moveToFirst()){
			do{
				String lista = consulta.getString(0);
				if(!lista.equals("android_metadata") && !lista.equals("amigos") && !lista.equals("temporal") && !lista.equals("publicos") && !lista.equals("todos")){
					lista = lista.replaceAll("000", " ");;
					todolistas.add(lista);
				}
			}while(consulta.moveToNext());
		}
		return todolistas;
	}
	
	public ArrayList<ListaLibros> ListadoListas(){
		SQLiteDatabase bd = getReadableDatabase();
		ArrayList<ListaLibros> todolistas = new ArrayList();
		Cursor consulta = null;
			consulta = bd.rawQuery("select name from sqlite_master where type = 'table';", null);
		if(consulta.moveToFirst()){
			do{
				String lista = consulta.getString(0);
				if(!lista.equals("android_metadata") && !lista.equals("amigos") && !lista.equals("temporal") && !lista.equals("publicos") && !lista.equals("todos")){
					lista = lista.replaceAll("000", " ");
					ListaLibros lista_libros = new ListaLibros(lista);
					lista_libros = ListaDeLibros(lista);
					todolistas.add(lista_libros);
				}
			}while(consulta.moveToNext());
		}
		return todolistas;
	}
	
	/*Borra un amigo en concreto*/
	public void BorrarLibroLista(String id, String lista)throws SQLException{
		SQLiteDatabase bd = getWritableDatabase();
		lista = lista.replaceAll(" ", "000");
		String delete1 = "DELETE FROM "+lista+ " WHERE id='"+id+"'";
		bd.execSQL(delete1);
		
	}
	
/****  MANEJO TEMPORAL  *****/
	
	/*Inserta libro en temporal */
	
	public void InsertarTemporal(Libro book) throws SQLException{
		SQLiteDatabase bd = getWritableDatabase();
		String id = book.getId();
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
		String insert = "INSERT INTO temporal (id,isbn, title, subtitle, author, photo, editorial, description, language,averageRating) VALUES(\""+
				id+"\",\""+isbn +"\",\""+title+"\",\""+subtitle+"\",\""+authors+"\",\""+photo +"\",\""+publisher+"\",\""+description+"\",\""+language+"\",\""+Float.toString(average)+"\");";
		bd.execSQL(insert);
		
	}
	
	
	/*Devuelve el libro temporal (Asumimos que siempre habr� un libro)*/
	
	public Libro DetalleTemporal() throws SQLException{
		SQLiteDatabase bd = getReadableDatabase();
		Libro libro= null;
		Cursor consulta = null;
		consulta = bd.rawQuery("SELECT * FROM temporal", null);
		
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
	public void BorrarTemporal() throws SQLException{
		SQLiteDatabase bd = getWritableDatabase();
		String delete = "DELETE FROM temporal";
		bd.execSQL(delete);
		
	}
	
}

