<?php

  if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $ID_ITEM = $_POST['ID_ITEM'];
    $STA_CHECK = $_POST['STA_CHECK'];

    require_once("connect.php");

    $query = $pdo->prepare("INSERT INTO `item` (
      STA_CHECK, QUANTIDADE, VLR_UNITARIO,
      VLR_TOTAL, ID_LISTA, NME_PRODUTO,
      DSC_PRODUTO, UN_MEDIDA
    ) VALUES (
      '$STA_CHECK', '$QUANTIDADE', '$VLR_UNITARIO',
      '$VLR_TOTAL', '$ID_LISTA', '$NME_PRODUTO',
      '$DSC_PRODUTO', '$UN_MEDIDA');");

    $query->execute();

    if ($query) {
      $response['success'] = true;
      $response['message'] = "Successfully";

    } else {
      $response['success'] = false;
      $response['message'] = "Failure";

    }

  } else {
    $response['success'] = false;
    $response['message'] = "Error";

  }

  echo json_encode($response);

?>
