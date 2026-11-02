# TUGAS PRAKTIKUM 6 DPBO

# JANJI
Saya Rafi Subqhi Nuraziz dengan NIM 2408678 mengerjakan Tugas Praktikum 6 dalam mata kuliah Desain Pemrograman Berorientasi Objek untuk keberkahan-Nya maka saya tidak akan melakukan kecurangan seperti yang telah di spesifikasikan. Aamiin.

---

## 1. Desain Program
Program ini dibangun menggunakan bahasa Java (GUI Swing) dengan menerapkan paradigma Object-Oriented Programming (OOP) yang memisahkan logika, tampilan, dan objek permainan ke dalam 5 kelas utama:

**Class `App` (Main & Menu Controller)**:
Kelas utama yang mewarisi `JFrame` untuk menampung seluruh antarmuka permainan. Kelas ini bertugas menampilkan Main Menu pada saat program pertama kali dijalankan, mengontrol perpindahan dari layar menu ke layar permainan, serta memuat dan memutar audio (BGM) menggunakan pustaka `javax.sound.sampled`.

**Class `Logic` (Controller & Game Engine)**:
Kelas ini merupakan inti dari logika permainan. Kelas ini mengimplementasikan `ActionListener` dan `KeyListener` untuk menangani *input* keyboard (tombol Spasi dan R) serta pergerakan otomatis (*game loop*). Di dalamnya terdapat logika untuk menggerakkan burung dan pipa, sistem deteksi tabrakan (*collision detection*) untuk memicu *Game Over*, perhitungan skor saat melewati pipa, dan fungsi me-*restart* permainan.

**Class `View` (GUI / Renderer)**:
Kelas turunan `JPanel` ini bertanggung jawab penuh untuk menggambar visualisasi permainan ke layar (*rendering*). Kelas ini mengambil data posisi dari kelas `Logic` lalu menggambar aset gambar (burung, pipa atas, pipa bawah) menggunakan metode `paintComponent`, serta menampilkan dan memperbarui teks `JLabel` untuk skor secara *real-time*.

**Class `Player` (Model)**:
Kelas *blueprint* yang menyimpan seluruh data pemain (burung). Kelas ini menampung atribut koordinat (X, Y), ukuran lebar dan tinggi, nilai kecepatan vertikal saat jatuh atau melompat, serta objek gambar burung tersebut.

**Class `Pipe` (Model)**:
Kelas *blueprint* yang merepresentasikan rintangan pipa. Kelas ini menyimpan atribut koordinat, dimensi, objek gambar (pipa atas atau pipa bawah), serta status *boolean* untuk menandai apakah pipa tersebut sudah dilewati oleh pemain agar skor tidak dihitung berulang kali.

## 2. Penjelasan Alur
Program ini merupakan game interaktif Desktop berbasis GUI (Swing). Berikut adalah alur kerjanya:

1.  **Inisialisasi & Main Menu:**
    Saat program dijalankan, `App` membuat jendela aplikasi dan menampilkan panel `mainMenu` yang berisi tombol "Mulai Permainan" dan "Keluar". Permainan belum berjalan sampai tombol diklik.
2.  **Mulai Permainan:**
    Ketika tombol "Mulai Permainan" ditekan, *layout* menu dihapus dari *frame*, *Background Music* (BGM) mulai diputar berulang (*looping*), dan kelas `Logic` serta `View` diinisialisasi. *Timer game loop* (berjalan pada 60 FPS) dan *timer* memunculkan pipa (tiap 1,5 detik) otomatis dimulai.
3.  **Gameplay (Lompat & Rintangan):**
    Pemain menekan tombol "Spasi" untuk memberikan kecepatan vertikal negatif (burung melompat naik), sementara sistem secara konstan menarik burung ke bawah (gravitasi). Pipa digenerate dengan posisi Y yang diacak menggunakan fungsi matematika, lalu bergerak ke kiri secara konstan.
4.  **Penambahan Skor:**
    Dalam perulangannya, sistem mengecek posisi X pemain. Jika pemain berhasil melewati koordinat batas pipa, skor akan bertambah +1 dan `JLabel` skor pada tampilan akan otomatis diperbarui.
5.  **Game Over & Restart:**
    Jika deteksi tabrakan mendeteksi perpotongan antara posisi pemain dengan pipa, atau pemain jatuh menyentuh batas layar, variabel `gameOver` berubah menjadi *true*. Seluruh pergerakan (*timer*) dan audio BGM dihentikan seketika. Pemain kemudian dapat menekan tombol "R" untuk mereset skor, mengembalikan burung ke titik tengah, membersihkan daftar pipa, dan menjalankan kembali audio serta pergerakan rintangan.

## 3. Dokumentasi

Berikut adalah dokumentasi lengkap berupa video screenrecord dari awal program dijalankan hingga selesai (mencakup Main Menu, gameplay, penambahan skor, audio BGM, Game Over, dan fitur Restart):

[Klik di sini untuk menonton Video Screenrecord](Dokumentasi/Dokumentasi.mp4)