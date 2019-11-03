<?php

	error_reporting(0);
	$connetion = "mysql:host=localhost;dbname=id10323298_easylist";

	try {
		$pdo = new PDO($connetion, "id10323298_easylist", "engenhariadesoftware") or die();
	} catch (PDOException $e) {
		if($e->getCode() == 2002){
			echo "Não foi possível conectar ao servidor.";
		}else if($e->getCode() == 1049){
			echo "Não foi possível conectar ao banco de dados.";
		}else if($e->getCode() == 1045){
			echo "Não foi possivel fazer a conexão.";
		}else{
			echo $e->getMessage();
		}
	}

?>
