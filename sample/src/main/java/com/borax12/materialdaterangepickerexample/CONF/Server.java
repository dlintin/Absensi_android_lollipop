package com.borax12.materialdaterangepickerexample.CONF;

/**
 * Created by KUNCORO on 24/03/2017.
 */

public class Server {
    //    public static final String UTAMA = "http://192.168.1.70";
    public static final String UTAMA ="http://10.0.2.2";
    public static final String LOGIN = UTAMA +"/absensi/android/android_login/login.php";
    public static final String REGISTER = UTAMA +"/absensi/android/android_login/register.php";
    public static final String ANDROID_TABLE = "/absensi/android/android_table";
    public static final String BLUM_SCAN = UTAMA + ANDROID_TABLE + "/laporan_blmhadir.php";
    public static final String RANGE_TANGGAL = UTAMA + ANDROID_TABLE + "/getFixtureRanged.php?tglawal=";
    public static final String TERBARU = UTAMA + ANDROID_TABLE + "/getFixture.php";
    public static final String AKUMULASI = UTAMA + ANDROID_TABLE + "/akumulasi.php?tglawal=";
    public static final String STATUS_ABSENSI = UTAMA + ANDROID_TABLE + "/statusabsensi.php?tglawal=";



}
