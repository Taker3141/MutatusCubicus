#version 400 core

in vec2 textureCoord;

out vec4 outColor;

uniform sampler2D colorTexture;

void main(void)
{
	outColor = texture(colorTexture, textureCoord);
}