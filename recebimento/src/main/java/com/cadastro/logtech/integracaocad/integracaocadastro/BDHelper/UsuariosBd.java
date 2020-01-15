package com.cadastro.logtech.integracaocad.integracaocadastro.BDHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cadastro.logtech.integracaocad.integracaocadastro.model.Usuarios;

import java.util.ArrayList;

public class UsuariosBd extends SQLiteOpenHelper {

    private static final String DATABASE ="bdusuarios";
    private  static final int VERSION = 1;
    private final String TABLE = "usuario";
    private Long id;
    private String nome;
    private String senha;


    public UsuariosBd (Context context){
        super(context, DATABASE,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String usuario = "CREATE TABLE usuario(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nome TEXT NOT NULL, celular TEXT NOT NULL, senha TEXT NOT NULL);";
        db.execSQL(usuario);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String usuario = "DROP TABLE IF EXISTS usuario";
        db.execSQL(usuario);
    }

    // aqui salva
    public void salvarUsuario(Usuarios usuario){
        ContentValues values = new ContentValues();

        values.put("nome",usuario.getNome());
        values.put("celular",usuario.getCelular());
        values.put("senha",usuario.getSenha());

        getWritableDatabase().insert("usuario",null,values);
    }
    // metodo alterar concluído ↓ :D
    public void alterarUsuario(Usuarios usuario){
        ContentValues values = new ContentValues();

        values.put("nome",usuario.getNome());
        values.put("celular",usuario.getCelular());
        values.put("senha",usuario.getSenha());

        String[] args = {usuario.getId().toString()};
        getWritableDatabase().update("usuario",values,"id=?",args);

    }

    public void deletarUsuario(Usuarios usuario){
        String[] args = {usuario.getId().toString()};
        getWritableDatabase().delete("usuario","id=?",args);
    }

    public Usuarios montaUsuario(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }

        Long id = cursor.getLong(cursor.getColumnIndex("id"));
        String nome = cursor.getString(cursor.getColumnIndex("nome"));
        String senha = cursor.getString(cursor.getColumnIndex("senha"));

        Usuarios usuarios = new Usuarios(id, nome, senha);
        return usuarios;

    }

    public Usuarios findByLogin(String usuario, String senha) {
        String sql = "SELECT * FROM " + TABLE + " WHERE nome = ? AND senha = ?";
        String[] selectionArgs = new String[] { usuario, senha };
        Cursor cursor = getReadableDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        return montaUsuario(cursor);
    }


    // lista - mostrar

    public ArrayList<Usuarios> getLista(){
        String[] columns ={"id","nome","celular","senha"};
        Cursor cursor = getWritableDatabase().query("usuario",columns,null,null,null,null,null,null);
        ArrayList<Usuarios> usuarios = new ArrayList<Usuarios>();

        while (cursor.moveToNext()){
            Usuarios usuario = new Usuarios(id,nome,senha);
            usuario.setId(cursor.getLong(0));
            usuario.setNome(cursor.getString(1));
            usuario.setCelular(cursor.getString(2));
            usuario.setSenha(cursor.getString(3));

            usuarios.add(usuario);
        }
        return usuarios;
    }

    public boolean validaLogin(String usuario, String senha) {
        SQLiteDatabase db = getWritableDatabase();
        Usuarios user =  findByLogin(usuario, senha);
        if (user == null || user.getNome() == null || user.getSenha() == null) {
            return false;
        }
        String informado = usuario + senha;
        String esperado = user.getNome() + user.getSenha();
        if (informado.equals(esperado)) {
            return true;
        }
        return false;

    }






}
