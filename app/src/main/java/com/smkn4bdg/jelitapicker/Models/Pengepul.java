package com.smkn4bdg.jelitapicker.Models;

public class Pengepul {
    private String id;
    private String nama;
    private String foto;
    private String jenis_kelamin;
    private String alamat;
    private String kota;
    private String kelurahan;
    private String kecamatan;
    private String username;
    private String password;
    private String email;
    private String no_telp;

    public Pengepul(){

    }

    public Pengepul(String id, String nama, String foto, String jenis_kelamin, String alamat, String kota, String kelurahan, String kecamatan, String username, String password, String email, String no_telp) {
        this.id = id;
        this.nama = nama;
        this.foto = foto;
        this.jenis_kelamin = jenis_kelamin;
        this.alamat = alamat;
        this.kota = kota;
        this.kelurahan = kelurahan;
        this.kecamatan = kecamatan;
        this.username = username;
        this.password = password;
        this.email = email;
        this.no_telp = no_telp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }
}
