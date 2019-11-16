<?php

  if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $STA_CHECK = $_POST['STA_CHECK'];
    $QUANTIDADE = $_POST['QUANTIDADE'];
    $VLR_UNITARIO = $_POST['VLR_UNITARIO'];
    $VLR_TOTAL = $_POST['VLR_TOTAL'];
    $ID_LISTA = $_POST['ID_LISTA'];
    $NME_PRODUTO = $_POST['NME_PRODUTO'];
    $DSC_PRODUTO = $_POST['DSC_PRODUTO'];
    $UN_MEDIDA = $_POST['UN_MEDIDA'];

    require_once("connect.php");

    if($DSC_PRODUTO == "") {

      $query = $pdo->prepare("INSERT INTO `item` (
        STA_CHECK, QUANTIDADE, VLR_UNITARIO,
        VLR_TOTAL, ID_LISTA, NME_PRODUTO,
        UN_MEDIDA
      ) VALUES (
        '$STA_CHECK', '$QUANTIDADE', '$VLR_UNITARIO',
        '$VLR_TOTAL', '$ID_LISTA', '$NME_PRODUTO',
        '$UN_MEDIDA');");

    } else {

        $query = $pdo->prepare("INSERT INTO `item` (
          STA_CHECK, QUANTIDADE, VLR_UNITARIO,
          VLR_TOTAL, ID_LISTA, NME_PRODUTO,
          DSC_PRODUTO, UN_MEDIDA
        ) VALUES (
          '$STA_CHECK', '$QUANTIDADE', '$VLR_UNITARIO',
          '$VLR_TOTAL', '$ID_LISTA', '$NME_PRODUTO',
          '$DSC_PRODUTO', '$UN_MEDIDA');");

    }

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
