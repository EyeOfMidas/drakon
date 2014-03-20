drakon
======

Simple java SSH server with password authentication

Replace the drakon.keystore with your own signed cert!

keytool -genkey -alias drakon -keyalg RSA -keystore drakon.keystore

keytool -export -alias drakon -file drakon.cer -keystore drakon.keystore
