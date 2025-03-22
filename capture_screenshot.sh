#!/bin/bash
# Wait for the simulation to start and capture a screenshot
sleep 5  # Give time for the simulation to initialize
xwd -silent -id $(xwininfo -root -tree | grep "Ecosystem Simulation" | awk '{print $1}') | convert xwd:- png:/root/CascadeProjects/PORTFOLIO/PORTFOLIO_windsurf-/images/ecosystem.png
