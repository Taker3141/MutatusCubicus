#version 400 core

in vec2 position;

out vec2 textureCoord;

uniform vec2 warp;

void main(void)
{
	gl_Position = vec4(position, 0.0, 1.0);
	textureCoord = vec2(position.x * warp.x + 0.5, position.y * warp.y + 0.5);
}