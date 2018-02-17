@echo off

cd curls

	set /P link=Enter Repository Link: 

	call git clone %link%
