<?php

  if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $NME_LISTA = $_POST['NME_LISTA'];
    $UID_USUARIO = $_POST['UID_USUARIO'];
    $USER_ID = null;

    require_once("connect.php");

    $query = $pdo->prepare("SELECT ID_USUARIO FROM `usuario` WHERE `UID` = '$UID_USUARIO';");
    $query->execute();

    while($ID = $query->fetch(PDO::FETCH_ASSOC)) {
      $USER_ID = $ID['ID_USUARIO'];

    }

    $query = $pdo->prepare("INSERT INTO `lista` (NME_LISTA, ID_USUARIO) VALUES ('$NME_LISTA','$USER_ID');");
    $query->execute();

    if ($query) {
      $response['success'] = true;
      $response['message'] = "Lista adicionada";

    } else {
      $response['success'] = false;
      $response['message'] = "Erro ao adicionar lista";

    }

  } else {
    $response['success'] = false;
    $response['message'] = "Erro na solicitação";

  }

  echo json_encode($response);

?>
