<?php

  if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $ID_LISTA = $_POST['ID_LISTA'];

    require_once("connect.php");

    $query = $pdo->prepare("DELETE FROM `item` WHERE `ID_LISTA` = '$ID_LISTA';");
    $query->execute();

    if ($query) {
      $query = $pdo->prepare("DELETE FROM `lista` WHERE `ID_LISTA` = '$ID_LISTA';");
      $query->execute();

      if ($query) {
        $response['success'] = true;
        $response['message'] = "Lista deletada";

      } else {
        $response['success'] = false;
        $response['message'] = "Erro ao deletar lista";

      }
      
    } else {
      $response['success'] = false;
      $response['message'] = "Erro ao deletar itens";
    }



  } else {
    $response['success'] = false;
    $response['message'] = "Erro na solicitação";
  }

  echo json_encode($response);

?>
