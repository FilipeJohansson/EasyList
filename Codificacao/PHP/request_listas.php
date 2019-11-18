<?php

  header("Context-type:application/json");

  require_once("connect.php");

  $UID_USUARIO = $_GET['UID_USUARIO'];
  $USER_ID = null;

  $query = $pdo->prepare("SELECT ID_USUARIO FROM `usuario` WHERE `UID` = '$UID_USUARIO';");
  $query->execute();

  while($ID = $query->fetch(PDO::FETCH_ASSOC)) {
    $USER_ID = $ID['ID_USUARIO'];

  }

  $query = $pdo->prepare("SELECT ID_LISTA, NME_LISTA, ID_COMPARTILHADO FROM `lista` WHERE `ID_USUARIO` = '$USER_ID' OR `ID_COMPARTILHADO` = '$USER_ID';");
  $query->execute();

  $response = array();

  while($row = $query->fetch(PDO::FETCH_ASSOC)) {

    array_push($response,
    array(
      'ID_LISTA' =>$row['ID_LISTA'],
      'NME_LISTA' =>$row['NME_LISTA'],
      'ID_COMPARTILHADO' =>$row['ID_COMPARTILHADO'])
    );

  }

  echo json_encode($response);

?>
