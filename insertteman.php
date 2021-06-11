<?php
	$server		= "localhost";
	$user		= "root";
	$password	= "";
	$namadb		= "teman";

	$conn = mysqli_connect($server, $user, $password, $namadb) or die("Koneksi gagal");

	$nama 	= $_POST['nama'];
	$telpon	= $_POST['telpon'];

	class emp{}

	if (empty($nama) || empty($telpon)) {
		
		$response = new emp();
		$response->success = 0;
		$response->message = "Data tidak boleh kosong";
		die(json_encode($response));
	}else{
		$query = mysqli_query($conn,"INSERT INTO datatemanbaru (nama,telpon) values ($nama,$telpon)");
		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Data Berhasil Disimpan";
			die(json_encode($response));
		}else{
			$response = new emp();
			$response->success = 0;
			$response-> message = "Gagal simpan Data";
			die(json_encode($response));
		}
	}
?>
