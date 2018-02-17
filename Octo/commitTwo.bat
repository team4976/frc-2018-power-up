@echo off

cd curls

	set /P message=Enter Commit Message: 

	call git add *
	call git commit -m "%message%"
	call git push
