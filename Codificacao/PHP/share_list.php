<?php

  if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $ID_LISTA = $_POST['ID_LISTA'];
    $ID_COMPARTILHADO = $_POST['ID_COMPARTILHADO'];
    $ID_COMP = null;
    $COMPARTILHADO = false;

    require_once("connect.php");

    $query = $pdo->prepare("SELECT ID_COMPARTILHADO FROM `lista` WHERE ID_LISTA = '$ID_LISTA';");
    $query->execute();

    while($ID = $query->fetch(PDO::FETCH_ASSOC)) {
      $ID_COMP = $ID['ID_COMPARTILHADO'];
    }

    if ($ID_COMP == NULL) {
        $query = $pdo->prepare("UPDATE `lista` SET ID_COMPARTILHADO = '$ID_COMPARTILHADO' WHERE ID_LISTA = '$ID_LISTA';");
        $query->execute();

        $COMPARTILHADO = true;

    }

    if ($COMPARTILHADO) {
      $response['success'] = true;
      $response['message'] = "Lista compartilhada";

    } else {
      $response['success'] = false;
      $response['message'] = "Essa lista já está sendo compartilhada\nMáximo de 1 compartilhamento por lista";

    }

  } else {
    $response['success'] = false;
    $response['message'] = "Erro na solicitação";

  }

  echo json_encode($response);

?>
