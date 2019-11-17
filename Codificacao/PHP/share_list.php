<?php

  if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $EMAIL = $_POST['EMAIL'];
    $ID_LISTA = $_POST['ID_LISTA'];
    $ID_COMPARTILHADO = null;
    $USER_ID = null;
    $COMPARTILHADO = false;

    require_once("connect.php");

    $query = $pdo->prepare("SELECT ID_COMPARTILHADO FROM `lista` WHERE ID_LISTA = '$ID_LISTA';");
    $query->execute();

    while($ID = $query->fetch(PDO::FETCH_ASSOC)) {
      $ID_COMPARTILHADO = $ID['ID_COMPARTILHADO'];
    }

    if ($ID_COMPARTILHADO == NULL) {
      $query = $pdo->prepare("SELECT ID_USUARIO FROM `usuario` WHERE `EMAIL` = '$EMAIL';");
      $query->execute();

      while($ID = $query->fetch(PDO::FETCH_ASSOC)) {
        $USER_ID = $ID['ID_USUARIO'];
      }

      if ($USER_ID != NULL) {
        $query = $pdo->prepare("UPDATE `lista` SET ID_COMPARTILHADO = '$USER_ID' WHERE ID_LISTA = '$ID_LISTA';");
        $query->execute();

        $COMPARTILHADO = true;

      } else {
        $response['success'] = false;
        $response['message'] = "Usuário não encontrado";
      }


    } else {
      $response['success'] = false;
      $response['message'] = "Essa lista já está sendo compartilhada\nMáximo de 1 compartilhamento por lista";
    }



    if ($COMPARTILHADO) {
      $response['success'] = true;
      $response['message'] = "Lista compartilhada";

    } else {
      $response['success'] = false;
      $response['message'] = "Erro ao compartilhar lista";

    }

  } else {
    $response['success'] = false;
    $response['message'] = "Erro na solicitação";

  }

  echo json_encode($response);

?>
