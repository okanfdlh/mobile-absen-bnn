# Absen BNN (Offline) — Rekap Absensi Apel Pagi per Bidang

Aplikasi Android native untuk menggantikan absensi manual berbasis kertas. Absensi dicatat **bukan per pegawai**, melainkan **rekap jumlah** per **bidang/divisi** per **tanggal**. Seluruh fitur berjalan **penuh offline** (data tersimpan lokal di SQLite via Room).

## About App

**Masalah yang diselesaikan**
- Input absensi apel pagi per bidang masih manual dan rawan salah hitung.
- Rekap harian/mingguan/bulanan perlu dihitung cepat tanpa spreadsheet terpisah.

**Solusi**
- Admin menginput jumlah pegawai hadir per bidang per hari, plus rincian keterangan yang kurang.
- Aplikasi otomatis menghitung `kurang = jumlah - hadir` dan memvalidasi total rincian keterangan.
- Laporan harian/mingguan/bulanan dihitung dari data harian di database lokal (tanpa internet).

## Fitur

- Login offline (akun lokal di SQLite) + penyimpanan sesi lokal (DataStore).
- Input/edit absensi harian per bidang dengan validasi real-time.
- Rekap:
  - Harian (per tanggal)
  - Mingguan (berdasarkan `week_key` format ISO, contoh `2026-W09`)
  - Bulanan (berdasarkan `month_key` format `YYYY-MM`, contoh `2026-02`)
- Riwayat: daftar minggu/bulan yang pernah memiliki data.
- Master Bidang: tambah/edit bidang dan aktif/nonaktif.
- Akses berbasis role:
  - **ADMIN**: input/edit absensi + kelola master bidang + lihat laporan.
  - **VIEWER**: hanya lihat laporan & riwayat (tanpa edit).

## Business Rules (Validasi)

- `kurang = jumlah - hadir`
- `hadir <= jumlah`
- Semua angka tidak boleh negatif.
- Total rincian keterangan harus sama dengan `kurang`:
  - `dinas + terlambat + sakit + cuti + izin + off_dinas + lain_lain == kurang`
- Unik per bidang per tanggal: **1 data** untuk `(division_id, attendance_date)`.

## Tech Stack

- Kotlin
- Jetpack Compose (UI)
- MVVM + Repository Pattern + UseCase
- Room (SQLite) + KSP
- Coroutines + Flow / StateFlow
- DataStore Preferences (session)

## Struktur Folder (Ringkas)

Di dalam module `app/src/main/java/com/example/absenbnn`:

- `data/local/entity` — Room entities
- `data/local/dao` — DAO & query report
- `data/local/database` — AppDatabase + seed data
- `data/repository` — Repository layer
- `domain/model` — Model domain
- `domain/usecase` — Use case (validasi, login, upsert, report)
- `ui/*` — Screen + ViewModel (Compose)
- `util` — util password hash & date keys

## Database (SQLite via Room)

Tabel utama:
- `users`
- `divisions`
- `attendance_daily`

Kunci unik (wajib):
- `attendance_daily`: `UNIQUE(division_id, attendance_date)`

Catatan:
- Rekap mingguan/bulanan **tidak wajib disimpan** sebagai tabel terpisah; dihitung dari `attendance_daily`.

## Akun Default & Seed Data

Saat database pertama kali dibuat, aplikasi melakukan seed:
- User default:
  - Username: `admin`
  - Password: `admin123`
  - Role: `ADMIN`
  - Password disimpan sebagai hash (PBKDF2 HMAC-SHA256).
- Division default:
  - `UMUM` — Bidang Umum
  - `SDM` — Bidang SDM
  - `KEU` — Bidang Keuangan
  - `OPS` — Bidang Operasional

## Setup & Install (Android Studio)

### Prasyarat

- Android Studio (disarankan versi terbaru stable)
- Java:
  - Untuk build Android (AGP/Gradle) gunakan **JDK 17** sebagai **Gradle JDK** (disarankan pakai Embedded JDK dari Android Studio).
  - Jika Java sistem kamu sudah **OpenJDK 25** (seperti `java --version` yang kamu kirim), itu tidak masalah untuk penggunaan umum, tapi tetap set **Gradle JDK = 17** agar Gradle Sync/build kompatibel.
- Android SDK:
  - Compile/Target SDK: 34
  - Min SDK: 26
- Koneksi internet hanya diperlukan saat pertama kali download dependency Gradle. Setelah itu aplikasi berjalan offline.

### Cara Menjalankan

1. Buka Android Studio.
2. Pilih **Open** lalu arahkan ke folder project:
   - `/Users/mac/Documents/trae_projects/absenBnn`
3. Tunggu proses **Gradle Sync** selesai.
4. Pilih device/emulator.
5. Klik **Run**.

### Konfigurasi Build

Gradle config utama:
- Root: `settings.gradle.kts`, `build.gradle.kts`, `gradle/libs.versions.toml`
- App module: `app/build.gradle.kts`

## Cara Pakai (User Guide)

### 1) Login

- Masuk menggunakan akun lokal.
- Default: `admin / admin123`.

### 2) Dashboard

Menu yang tersedia:
- Absen Hari Ini (Admin)
- Rekap Harian
- Rekap Mingguan
- Rekap Bulanan
- Riwayat
- Master Bidang (Admin)
- Logout

### 3) Absen Hari Ini (Admin)

Langkah:
1. Pilih tanggal (format `YYYY-MM-DD`).
2. Pilih bidang.
3. Isi `Jumlah` dan `Hadir`. Aplikasi menghitung `Kurang` otomatis.
4. Isi rincian `keterangan kurang` hingga totalnya sama dengan nilai `Kurang`.
5. Klik **Simpan**.

Catatan:
- Jika data untuk bidang & tanggal tersebut sudah ada, form otomatis masuk **mode edit** (tombol berubah menjadi **Update**).

### 4) Rekap Harian

1. Masukkan tanggal `YYYY-MM-DD`.
2. Klik **Muat**.
3. Lihat rekap per bidang + total seluruh bidang (termasuk persentase hadir).

### 5) Rekap Mingguan

1. Masukkan `week_key` (contoh `2026-W09`).
2. Klik **Muat**.

### 6) Rekap Bulanan

1. Masukkan `month_key` (contoh `2026-02`).
2. Klik **Muat**.

### 7) Riwayat

- Menampilkan daftar `week_key` dan `month_key` yang memiliki data.
- Klik salah satu item untuk membuka detail rekap.

### 8) Master Bidang (Admin)

- Tambah bidang: isi kode + nama + status aktif.
- Edit bidang: klik item bidang, ubah data, simpan.

## Catatan Keamanan

- Password tidak disimpan plaintext.
- Hash password menggunakan PBKDF2 HMAC-SHA256 dengan salt unik per user.

## Troubleshooting

- Gradle Sync gagal:
  - Pastikan Android Studio memakai **Gradle JDK 17**: Android Studio → Settings/Preferences → Build, Execution, Deployment → Build Tools → Gradle → Gradle JDK.
  - Jika Java sistem kamu JDK 25, jangan dipakai sebagai Gradle JDK untuk project ini.
  - Pastikan koneksi internet tersedia untuk download dependency pertama kali.
  - Coba **File → Invalidate Caches / Restart**.
- Database seed tidak muncul:
  - Hapus aplikasi dari device/emulator lalu jalankan ulang untuk memicu pembuatan DB baru.

## Lisensi

Internal / sesuai kebutuhan organisasi.
