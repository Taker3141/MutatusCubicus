#version 400 core

in vec3 fragmentColor;

out vec4 outColor;

void main(void)
{
	outColor = vec4(fragmentColor, 1.0);
}