package fengfei.forest.slice.impl;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.LogicalSlice;
import fengfei.forest.slice.config.FunctionType;
import fengfei.forest.slice.config.SliceKind;
import fengfei.forest.slice.config.xml.SliceConfig;
import fengfei.forest.slice.exception.ErrorSliceConfigException;

class PhysicalSliceFactory {

	public <Source> PhysicalSlice create(
			SliceConfig sliceConfig,
			LogicalSlice<Source> logicalSlice,
			FunctionType functionType) {
		SliceKind kind = sliceConfig.getSliceKind();
		Function function = sliceConfig.getFunction();
		if (kind != SliceKind.Physical) {
			throw new ErrorSliceConfigException(
					"SliceKind shuild be Physical,but is Logical.  SliceConfig config error for silce id: " + sliceConfig
							.getId());
		}

		boolean isValidate = validateFunction(functionType, function);
		if (!isValidate) {
			throw new ErrorSliceConfigException(
					"SliceConfig Function config error for silce id: " + sliceConfig.getId() + ".");
		}
		//
		PhysicalSlice slice = new PhysicalSlice();

		slice.setId(sliceConfig.getId());
		slice.addExtraInfo(logicalSlice.getExtraInfo());
		slice.addExtraInfo(sliceConfig.getExtraInfo());
		slice.setPhysical(true);
		slice.setSliceId(logicalSlice.getSliceId());
		slice.setWeight(sliceConfig.getWeight());
		slice.setFunction(function);

		return slice;
	}

	private boolean validateFunction(FunctionType functionType, Function function) {
		switch (functionType) {
		case ReadWrite:
			if (function == Function.Read || function == Function.Write || function == Function.ReadWrite || function == Function.Normal) {
				return true;
			}

			break;
		case MasterSlave:
			if (function == Function.Master || function == Function.Slave || function == Function.Normal) {
				return true;
			}
			break;
		case Equality:
			return true;

		default:
			break;

		}
		return false;
	}
}
