################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../cmdLine.c \
../cmdLineFunctions.c 

O_SRCS += \
../cmdLine.o \
../cmdLineFunctions.o 

OBJS += \
./cmdLine.o \
./cmdLineFunctions.o 

C_DEPS += \
./cmdLine.d \
./cmdLineFunctions.d 


# Each subdirectory must supply rules for building sources it contributes
%.o: ../%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


