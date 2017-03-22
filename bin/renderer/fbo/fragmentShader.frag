#version 400 core

in vec2 textureCoord;

out vec4 outColor;

uniform sampler2D colorTexture;
uniform bool green;

void main(void)
{
	outColor = texture(colorTexture, textureCoord);
	if(!green) return;
	outColor.r *= 0.8;
	outColor.b *= 0.8;
}