#version 400 core

in vec2 position;
in vec2 textureCoord;

out vec2 passTextureCoord;

uniform vec2 translation;
uniform vec2 displaySize;

void main(void)
{
	vec2 positionOnScreen = vec2(position.x, position.y - 2);
	positionOnScreen = positionOnScreen + translation * (2 / displaySize);
	gl_Position = vec4(positionOnScreen, 0.0, 1.0);
	passTextureCoord = textureCoord;
}