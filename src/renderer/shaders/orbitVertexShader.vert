#version 400 core

in vec3 position;
in float count;

out vec3 fragmentColor;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lineColor;
uniform vec3 skyColor;

void main(void)
{
	gl_Position = viewMatrix * projectionMatrix * vec4(position, 1.0);
	fragmentColor = lineColor;
}