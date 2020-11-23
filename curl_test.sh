echo "--------------------------------------------------"
echo "Monster Trading Cards Game"
echo "--------------------------------------------------"
echo "CURL Testing for Monster Trading Cards Game"
echo ""
echo "1) Messages"
echo "1.1) Create messages"
curl -X POST http://localhost:8080/messages --header "Content-Type: application/json" -d "{\"message\":\"Eine neue Nachricht\"}"
echo ""
curl -X POST http://localhost:8080/messages --header "Content-Type: application/json" -d "{\"message\":\"Eine weitere Nachricht\"}"
echo ""
echo "1.2) List all messages"
curl http://localhost:8080/messages
echo ""
echo "1.3) List message with ID 1"
curl http://localhost:8080/messages/1
echo ""
echo "1.4) Update message with ID 1"
curl -X PUT http://localhost:8080/messages/1 --header "Content-Type: application/json" -d "{\"message\":\"Eine neue Nachricht 2.0\"}"
echo ""
curl http://localhost:8080/messages/1
echo ""
echo "1.5) Delete message with ID 1"
curl -X DELETE http://localhost:8080/messages/1
echo ""
echo "Should return Not Found:"
curl http://localhost:8080/messages/1
echo ""
echo ""
echo "2) Users"
echo "2.1) Create users"
curl -X POST http://localhost:8080/users --header "Content-Type: application/json" -d "{\"username\":\"schrom\",\"password\":\"moritz\"}"
echo ""
curl -X POST http://localhost:8080/users --header "Content-Type: application/json" -d "{\"username\":\"kienboec\",\"password\":\"daniel\"}"
echo ""
curl -X POST http://localhost:8080/users --header "Content-Type: application/json" -d "{\"username\":\"altenhof\",\"password\":\"markus\"}"
echo ""
echo "Should fail (duplicate username):"
curl -X POST http://localhost:8080/users --header "Content-Type: application/json" -d "{\"username\":\"schrom\",\"password\":\"password\"}"
echo ""
echo "2.2) Login"
curl -X POST http://localhost:8080/login --header "Content-Type: application/json" -d "{\"username\":\"schrom\",\"password\":\"moritz\"}"
echo ""
echo "Should fail (wrong password):"
curl -X POST http://localhost:8080/login --header "Content-Type: application/json" -d "{\"username\":\"schrom\",\"password\":\"password\"}"