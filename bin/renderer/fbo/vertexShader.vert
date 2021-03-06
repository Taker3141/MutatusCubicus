#version 400 core

in vec2 position;

out vec2 textureCoord;

void main(void)
{
	gl_Position = vec4(position, 0.0, 1.0);
	textureCoord = vec2(position.x * 0.5 + 0.5, position.y * 0.5 + 0.5);
}