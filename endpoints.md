# REST


# Websockets

## User to server
### Name message
Sent when user picks a name

    - String name

### Prompt entry
Send when user selects a value for a prompt

    - int promptId // So we dont accidentally enter an old prompt into the next prompt phase
    - String answer

## Server to user
### Generic error message
Generic error message with error code interpretable by frontend and a message

    - int errorCode
    - String message

### Welcome message
Sent when server accepts users name and shows them currently connected users

    - String[] existingUsers

### New user connect message
Sent when a new user connects

    - String username

### Prompt message
Sent to prompt user for a new value

    - int promptId
    - String description

### Presentation message
Sent when its time to show the madlib results per user

    - String username
    - String text
