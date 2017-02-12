<?php
$servername = "localhost";
$username = "root";
$password = "";
$conn = new mysqli($servername, $username, $password);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
$sql = "CREATE DATABASE IF NOT EXISTS posts_db";
if (!$conn->query($sql) === TRUE) {
    echo "Erro ao criar banco de dados: " . $conn->error;
}
$sql = "CREATE TABLE IF NOT EXISTS posts_db.Posts (
id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, 
username VARCHAR(200),
text VARCHAR(500) NOT NULL,
date DATETIME NOT NULL,
photourl VARCHAR(1000),
latitude FLOAT(15,12),
longitude FLOAT(15,12)
)";
if ($conn->query($sql) === FALSE) {
    echo "Erro ao criar tabela: " . $conn->error;
}
$metodoHttp = $_SERVER['REQUEST_METHOD'];
if ($metodoHttp == 'POST') {
	$stmt = $conn->prepare(
		"INSERT INTO posts_db.Posts (username, text, date, photourl, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)");
    $json = json_decode(file_get_contents('php://input'));
    $username   = $json->{'username'};
    $text       = $json->{'text'};
    $date       = $json->{'date'};
    $photourl   = $json->{'photourl'};
    $latitude   = $json->{'latitude'};
    $longitude  = $json->{'longitude'};
    $stmt->bind_param("ssssdd", $username, $text, $date, $photourl, $latitude, $longitude);
    $stmt->execute();
    $stmt->close();
    $id = $conn->insert_id;
    $jsonRetorno = array("id"=>(int)$id);

    echo json_encode($jsonRetorno);

} else if ($metodoHttp == 'GET') {
    $jsonArray = array();
    $sql = "SELECT id, username, text, date, photourl, latitude, longitude FROM posts_db.Posts WHERE username = '".$_GET['username']."'";

    $segments = explode("/", parse_url($_SERVER["REQUEST_URI"], PHP_URL_PATH));
    $singleLine = false;
    if (!empty($segments)){
        $id = $segments[count($segments)-1];
        if (is_numeric($id)){
            $singleLine = true;
            $sql = $sql ." AND id = ". $id;
        }
    }

    $result = $conn->query($sql);
    if ($result && $result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            $jsonLinha = array(
                "id"         => $row["id"],
                "username"   => $row["username"],
                "text"       => $row["text"],
                "date"       => $row["date"],
                "photourl"   => $row["photourl"],
                "latitude"   => (float)$row["latitude"],
                "longitude"  => (float)$row["longitude"]);
            $jsonArray[] = $jsonLinha;  

            if ($singleLine){
                echo json_encode($jsonLinha);
                break;
            }  	    
        }
    }
    if (!$singleLine) {
        echo json_encode($jsonArray);
    }

} else if ($metodoHttp == 'PUT') {
    $stmt = $conn->prepare(
        "UPDATE posts_db.Posts SET username=?, text=?, date=?, photourl=?, latitude=?, longitude=? WHERE id=?");
    $json  = json_decode(file_get_contents('php://input'));
    $segments = explode("/", $_SERVER["REQUEST_URI"]);
    $id = $segments[count($segments)-1];
    $username   = $json->{'username'};
    $text       = $json->{'text'};
    $date       = $json->{'date'};
    $photourl   = $json->{'photourl'};
    $latitude   = $json->{'latitude'};
    $longitude  = $json->{'longitude'};
    $stmt->bind_param("ssssddi", $username, $text, $date, $photourl, $latitude, $longitude, $id);
    $stmt->execute();
    $stmt->close();
    $jsonRetorno = array("id"=>(int)$id);

    $image_path =  "upload/" . $id .".jpg";
    if (($photourl == NULL || $photourl == "") && file_exists($image_path)){
        unlink($image_path);
    }

    echo json_encode($jsonRetorno);

} else if ($metodoHttp == 'DELETE') {
    $stmt = $conn->prepare("DELETE FROM posts_db.Posts WHERE id=?");
    $segments = explode("/", $_SERVER["REQUEST_URI"]);
    $id = $segments[count($segments)-1];
    $stmt->bind_param("i", $id);
    $stmt->execute();
    $stmt->close();
    $jsonRetorno = array("id"=>(int)$id);

    $image_path =  "upload/" . $id .".jpg";
    if (file_exists($image_path)){
        unlink($image_path);
    }

    echo json_encode($jsonRetorno);
}
$conn->close();
?>