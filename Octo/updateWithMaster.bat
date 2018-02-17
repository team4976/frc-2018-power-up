@echo off

cd curls

	set /P branch=Enter Branch Name: 

	call git checkout master

	call git pull

	call git checkout %branch%

	call git pull

	call git merge master