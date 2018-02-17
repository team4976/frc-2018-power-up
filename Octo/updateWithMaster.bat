@echo off

cd curls

	set /P branch=Enter Branch Name: 

	call git checkout master

	call git checkout %branch%

	call git merge master