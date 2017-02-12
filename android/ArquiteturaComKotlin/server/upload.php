<?php
if ($_FILES["arquivo"]["error"] > 0) {
    // Bad request
    http_response_code(400);
    
} else {
    $id = $_POST["id"];
    $arquivo_destino = "upload/" . $id .".jpg";
    move_uploaded_file(
        $_FILES["arquivo"]["tmp_name"],
        $arquivo_destino);

    $servername = "localhost";
    $username = "root";
    $password = "";
    $conn = new mysqli($servername, $username, $password);
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    } 
    $stmt = $conn->prepare(
        "UPDATE posts_db.Posts SET photourl=? WHERE id=?");
    
    $stmt->bind_param("si", $arquivo_destino, $id);
    $stmt->execute();
    $stmt->close();
    $jsonRetorno = array("id"=>(int)$id);

    echo json_encode($jsonRetorno);

    http_response_code(200);

    $conn->close();
}
?>