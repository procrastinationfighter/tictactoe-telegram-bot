# Tic-Tac-Toe Telegram Bot
## Overview
This project is a bot for Telegram application that uses Apache Camel, Spring Boot, Docker and a Postgresql Database. The only functionality is the possibility to play with another player a tic-tac-toe game on a Telegram chat. 
## How to run
1. Download the repository. 
2. Change `camel.component.telegram.authorization-token` in `tictactoe-bot/src/main/resources/application.properties` to the authorization token of your bot.
3. Run `docker-compose up` in the main directory.
4. Communicate with the bot through command on a chat.
## Bot commands
`/newgame @User` - start a new game with the mentioned user, the player invoking this command will be playing as X and will be first

`/move @User <tile_no>` - make a move in a game versus given player on the tile with given tile number, the tiles numbers are:

0   |  1   |  2 

3   |  4   |  5

6   |  7   |  8

for the standard 3x3 board. Example: `/move @john 4` will make a move in the game versus @john on the tile in the middle.
## FAQ
#### Why are you not using Telegram games bot options or other cool Telegram options?
The purpose of this project was to learn to integrate two separate applications and the project was created for my university class, not to learn how to design amazing bots.
