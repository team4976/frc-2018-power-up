@echo off

cd curls

	set /P branch=Enter Branch Name: 

	call git checkout -b %branch% master