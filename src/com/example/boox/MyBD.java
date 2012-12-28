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
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyBD extends SQLiteOpenHelper {
	
	public MyBD(Context context) {
		super(context, "myfile.db", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
	arg0.execSQL("CREATE TABLE libros (isbn  VARCHAR(20), title VARCHAR(20), author VARCHAR(500), photo VARCHAR(500), editorial VARCHAR(50), description VARCHAR(1000), language VARCHAR(20), averageRating FLOAT);");
	arg0.execSQL("CREATE TABLE amigos (id  VARCHAR(20), name VARCHAR(20), surname VARCHAR(50),  cp INTEGER )");
	}
		
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	
	public void InsertarLibro(Libro book){
		SQLiteDatabase bd = getWritableDatabase();
		String title = book.getTitulo();
		String authors = book.getAutores().get(0);
		for(int i=0;i<book.getAutores().size();i++) authors.concat(','+ book.getAutores().get(i));
		String photo = book.getImageLinks().getMedium();
		String publisher=book.getEditorial();
		String description = book.getDescripcion();
		String language = book.getIdioma();
		Float average = book.getPuntuacionMedia();
		String insert = "INSERT INTO libros (isbn, title, author, photo, editorial, description, language,averageRating) VALUES('"+
						book.getIsbn()+"','"+title+"','"+authors+"','"+photo +"','"+publisher+"','"+description+"','"+language+"','"+Float.toString(average)+"');";
		bd.execSQL(insert);
	}
	
	public void InsertarAmigo(Persona user){
		SQLiteDatabase bd = getWritableDatabase();
		String insert = "INSERT INTO amigos (id, name, cp) VALUES('"+
						user.getId()+"','"+user.getNombre()+"','"+user.getCodigoPostal()+"');";
		bd.execSQL(insert);
	}

	public void BorrarDatos(){
		SQLiteDatabase bd = getWritableDatabase();
		String delete = "DELETE FROM libros";
		String delete1 = "DELETE FROM amigos";
		bd.execSQL(delete);
		bd.execSQL(delete1);
	}
	
	public Libro DetallesLibroId(String id){
		SQLiteDatabase bd = getReadableDatabase();
		Libro libro= new Libro();
		Cursor consulta = bd.rawQuery("SELECT * FROM libros WHERE ID= ? ", new String[]{id});
		if(consulta.moveToFirst()){
				libro.setIsbn(consulta.getString(0));
				libro.setTitulo(consulta.getString(1));
				String[] aux = consulta.getString(2).split(",");
				ArrayList<String> authors = new ArrayList<String>(); 
				for(int i=0;i<aux.length;i++)	authors.add(aux[i]);
				libro.setAutores(authors);
				VolumeInfo volume = new VolumeInfo();
				VolumeInfo.ImageLinks imagen = volume.new ImageLinks();
				imagen.setMedium(consulta.getString(3));
				libro.setImageLinks(imagen);
				libro.setEditorial(consulta.getString(4));
				libro.setDescripcion(consulta.getString(5));
				libro.setIdioma(consulta.getString(6));
				libro.setPuntuacionMedia(consulta.getFloat(7));				
		}
		return libro;
	}
	
	public Usuario DetallesUsuarioId(int id){
		SQLiteDatabase bd = getReadableDatabase();
		Usuario pers= (Usuario) new Persona(null);
		Cursor consulta = bd.rawQuery("SELECT * FROM amigos WHERE ID= ? ", new String[]{Integer.toString(id)});
		if(consulta.moveToFirst()){
				pers.setIdUsuario(consulta.getString(0));
				pers.setNombre(consulta.getString(1));		
				pers.setCodigoPostal(consulta.getString(3));
		}
		return pers;
	}
	
	
	public void CrearNuevaLista(String nombreLista){
		SQLiteDatabase bd = getWritableDatabase();
		String insert = "CREATE TABLE" + nombreLista + "(id  VARCHAR(20))";
		bd.execSQL(insert);
	}
	
	public void InsertarLibroEnLista(String lista, String id){
		SQLiteDatabase bd = getWritableDatabase();
		String insert = "INSERT INTO " + lista + "(isbn) VALUES('"+id+"');";
		bd.execSQL(insert);
	}
	
	
	
}

