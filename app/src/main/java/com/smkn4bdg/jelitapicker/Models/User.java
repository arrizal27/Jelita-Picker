package com.smkn4bdg.jelitapicker.Models;

public class User {
    private String id;
    private String nama;
    private String username;
    private String role;
    private String email;
    private String password;
    private String jenis_kelamin;
    private String no_tlp;
    private int jml_minyak;
    private int poin;
    private String alamat;
    private String kelurahan;
    private String kecamatan;
    private String kota;

    public User(String id, String nama, String username, String role, String email, String password, String jenis_kelamin, String no_tlp, int jml_minyak, int poin, String alamat, String kelurahan, String kecamatan, String kota) {
        this.id = id;
        this.nama = nama;
        this.username = username;
        this.role = role;
        this.email = email;
        this.password = password;
        this.jenis_kelamin = jenis_kelamin;
        this.no_tlp = no_tlp;
        this.jml_minyak = jml_minyak;
        this.poin = poin;
        this.alamat = alamat;
        this.kelurahan = kelurahan;
        this.kecamatan = kecamatan;
        this.kota = kota;
    }

    public User(){

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getNo_tlp() {
        return no_tlp;
    }

    public void setNo_tlp(String no_tlp) {
        this.no_tlp = no_tlp;
    }

    public int getJml_minyak() {
        return jml_minyak;
    }

    public void setJml_minyak(int jml_minyak) {
        this.jml_minyak = jml_minyak;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
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

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }
}
